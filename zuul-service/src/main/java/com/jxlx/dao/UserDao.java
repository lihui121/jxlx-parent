package com.jxlx.dao;

import com.jxlx.common.RestResponse;
import com.jxlx.model.User;
import com.jxlx.service.GenericRest;
import com.jxlx.utils.Rests;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

  @Autowired
  private GenericRest rest;
  
  @Value("${user.service.name}")
  private String userServiceName;

  @HystrixCommand
  public List<User> getUserList(User query) {
    ResponseEntity<RestResponse<List<User>>> resultEntity = rest.post("http://"+ userServiceName + "/user/getList",query, new ParameterizedTypeReference<RestResponse<List<User>>>() {});
    RestResponse<List<User>> restResponse  = resultEntity.getBody();
    if (restResponse.getCode() == 0) {
        return restResponse.getResult();
    }else {
        return null;
    }
  }

  @HystrixCommand
  public User addUser(User account) {
    String url = "http://" + userServiceName + "/user/add";
    ResponseEntity<RestResponse<User>> responseEntity = rest.post(url,account, new ParameterizedTypeReference<RestResponse<User>>() {});
    RestResponse<User> response = responseEntity.getBody();
    if (response.getCode() == 0) {
       return response.getResult();
    }{
       throw new IllegalStateException("Can not add user");
    }
  }

  @HystrixCommand
  public User authUser(User user) {
    String url = "http://" + userServiceName + "/user/auth";
    ResponseEntity<RestResponse<User>> responseEntity =  rest.post(url, user, new ParameterizedTypeReference<RestResponse<User>>() {});
    RestResponse<User> response = responseEntity.getBody();
    if (response.getCode() == 0) {
      return response.getResult();
   }{
      throw new IllegalStateException("Can not add user");
   }
  }

  @HystrixCommand
  public void logout(String token) {
    String url = "http://" + userServiceName + "/user/logout?token=" + token;
    rest.get(url, new ParameterizedTypeReference<RestResponse<Object>>() {});
  }
  public User getUserByTokenFb(String token){
    return new User();
  }
  
  /**
   * 调用鉴权服务
   * @param token
   * @return
   */
  @HystrixCommand(fallbackMethod="getUserByTokenFb")
  public User getUserByToken(String token) {
    String url = "http://" + userServiceName + "/user/get?token=" + token;
    ResponseEntity<RestResponse<User>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<User>>() {});
    RestResponse<User> response = responseEntity.getBody();
    if (response == null || response.getCode() != 0) {
       return null;
    }
    return response.getResult();
  }


  public User updateUser(User user) {
    return Rests.exc(() ->{
      String url = Rests.toUrl(userServiceName, "/user/update");
      ResponseEntity<RestResponse<User>> responseEntity =
          rest.post(url, user, new ParameterizedTypeReference<RestResponse<User>>() {});
      return responseEntity.getBody();
    }).getResult();
  }


  public User reset(String key, String password) {
    return Rests.exc(() -> {
      String url = Rests.toUrl(userServiceName, "/user/reset?key=" + key + "&password="+password);
      ResponseEntity<RestResponse<User>> responseEntity =
          rest.get(url,new ParameterizedTypeReference<RestResponse<User>>() {});
      return responseEntity.getBody();
    }).getResult();
  }

  public void resetNotify(String email, String url) {
//     Rests.exc(() -> {
//          String sendUrl = Rests.toUrl(userServiceName, "/user/resetNotify?email=" + email + "&url="+url);
//          rest.get(sendUrl,new ParameterizedTypeReference<RestResponse<Object>>() {});
//          return new Object();
//    });
  }
  
  
  
}
