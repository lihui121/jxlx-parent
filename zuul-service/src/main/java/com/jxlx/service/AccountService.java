package com.jxlx.service;

import com.jxlx.dao.UserDao;
import com.jxlx.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户登录，注册，个人信息服务
 * 
 */
@Service
public class AccountService {

  @Value("${domain.name}")
  private String domainName;

  @Autowired
  private UserDao userDao;

  public User getUserById(String id){
    User queryUser = new User();
    queryUser.setId(id);
    List<User> users =  getUserByQuery(queryUser);
    if (!users.isEmpty()) {
      return users.get(0);
    }
    return null;
  }
  
  public List<User> getUserByQuery(User query){
    List<User> users =  userDao.getUserList(query);
    return users;
  }
  
  public boolean addAccount(User account){
    userDao.addUser(account);
    return true;
  }
  
  
  
  public boolean isExist(String email){
    return getUser(email) != null;
  }

  private User getUser(String email) {
    User queryUser = new User();
    queryUser.setEmail(email);
    List<User> users =  getUserByQuery(queryUser);
    if (!users.isEmpty()) {
       return users.get(0);
    }
    return null;
  }




  /**
   * 调用重置通知接口
   * @param email
   */
  @Async
  public void remember(String email){
    userDao.resetNotify(email,"http://" + domainName + "/accounts/reset");
  }
  
  /**
   * 重置密码操作
   * @param key
   */
  public User reset(String key,String password){
    return userDao.reset(key,password);
  }
  


  public User updateUser(User user){
    return  userDao.updateUser(user);
  }

  public void logout(String token) {
    userDao.logout(token);
  }

  /**
   * 校验用户名密码并返回用户对象
   * @param username
   * @param password
   * @return
   */
  public User auth(String username, String password) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
       return null;
    }
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    try {
      user = userDao.authUser(user);
    } catch (Exception e) {
      return null;
    }
    return user;
  }

 
  
  
}
