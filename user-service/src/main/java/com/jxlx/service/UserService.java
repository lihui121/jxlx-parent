package com.jxlx.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.jxlx.common.UserException;
import com.jxlx.dao.UserDao;
import com.jxlx.domain.User;
import com.jxlx.utils.BeanHelper;
import com.jxlx.utils.HashUtils;
import com.jxlx.utils.JwtHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

  @Autowired
  private StringRedisTemplate redisTemplate;
  
  @Autowired
  private UserDao userDao;
  
  /**
   * 1.首先通过缓存获取
   * 2.不存在将从通过数据库获取用户对象
   * 3.将用户对象写入缓存，设置缓存时间5分钟
   * 4.返回对象
   * @param id
   * @return
   */
  public User getUserById(Long id) {
    String key = "user:"+id;
    String json =  redisTemplate.opsForValue().get(key);
    User user = null;
    if (Strings.isNullOrEmpty(json)) {
      user =  userDao.selectById(id);
      String string  = JSON.toJSONString(user);
      redisTemplate.opsForValue().set(key, string);
      redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    }else {
      user = JSON.parseObject(json,User.class);
    }
    return user;
  }

  public List<User> getUserByQuery(User user) {
    List<User> users = userDao.selectUserList(user);
    return users;
  }

  /**
   * 注册
   * @param user
   * @return
   */
  public boolean addAccount(User user) {
    user.setPassword(HashUtils.encryPassword(user.getPassword()));
    BeanHelper.onInsert(user);
    userDao.insert(user);
    return true;
  }


  public boolean enable(String key) {
    String email = redisTemplate.opsForValue().get(key);
    if (StringUtils.isBlank(email)) {
       throw new UserException(UserException.Type.USER_NOT_FOUND, "无效的key");
    }
    User updateUser = new User();
    updateUser.setEmail(email);
    updateUser.setEnable(1);
    userDao.update(updateUser);
    return true;
  }

  /**
   * 校验用户名密码、生成token并返回用户对象
   * @param username
   * @param password
   * @return
   */
  public User auth(String username, String password) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
      throw new UserException(UserException.Type.USER_AUTH_FAIL,"User Auth Fail");
    }
    User user = new User();
    user.setUsername(username);
    user.setPassword(HashUtils.encryPassword(password));
    List<User> list =  getUserByQuery(user);
    if (!list.isEmpty()) {
       User retUser = list.get(0);
       onLogin(retUser);
       return retUser;
    }
    throw new UserException(UserException.Type.USER_AUTH_FAIL,"User Auth Fail");
  }

  private void onLogin(User user) {
    String token =  JwtHelper.genToken(ImmutableMap.of("email", user.getEmail(), "name", user.getUsername(),"ts",Instant.now().getEpochSecond()+""));
    renewToken(token,user.getEmail());
    user.setToken(token);
  }

  private String renewToken(String token, String email) {
    redisTemplate.opsForValue().set(email, token);
    redisTemplate.expire(email, 30, TimeUnit.MINUTES);
    return token; 
  }

  public User getLoginedUserByToken(String token) {
    Map<String, String> map = null;
    try {
      map = JwtHelper.verifyToken(token);
    } catch (Exception e) {
      throw new UserException(UserException.Type.USER_NOT_LOGIN,"User not login");
    }
    String email =  map.get("email");
    Long expired = redisTemplate.getExpire(email);
    if (expired > 0L) {
      renewToken(token, email);
      User user = getUserByEmail(email);
      user.setToken(token);
      return user;
    }
    throw new UserException(UserException.Type.USER_NOT_LOGIN,"user not login");
    
  }

  private User getUserByEmail(String email) {
    User user = new User();
    user.setEmail(email);
    List<User> list = getUserByQuery(user);
    if (!list.isEmpty()) {
      return list.get(0);
    }
    throw new UserException(UserException.Type.USER_NOT_FOUND,"User not found for " + email);
  }

  public void invalidate(String token) {
    Map<String, String> map = JwtHelper.verifyToken(token);
    redisTemplate.delete(map.get("email"));
  }

  @Transactional(rollbackFor = Exception.class)
  public User updateUser(User user) {
     if (user.getEmail() == null) {
        return null;
     }
     if (!Strings.isNullOrEmpty(user.getPassword()) ) {
        user.setPassword(HashUtils.encryPassword(user.getPassword()));
     }
     userDao.update(user);
     return userDao.selectByEmail(user.getEmail());
  }

//  public void resetNotify(String email,String url) {
//    String randomKey = "reset_" + RandomStringUtils.randomAlphabetic(10);
//    redisTemplate.opsForValue().set(randomKey, email);
//    redisTemplate.expire(randomKey, 1,TimeUnit.HOURS);
//    String content = url +"?key="+  randomKey;
//    mailService.sendSimpleMail("房产平台重置密码邮件", content, email);
//
//  }

  public String getResetKeyEmail(String key) {
    return  redisTemplate.opsForValue().get(key);
  }

  public User reset(String key, String password) {
    String email = getResetKeyEmail(key);
    User updateUser = new User();
    updateUser.setEmail(email);
    updateUser.setPassword(HashUtils.encryPassword(password));
    userDao.update(updateUser);
    return getUserByEmail(email);
  }

}
