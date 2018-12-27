package com.jxlx.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;

import java.util.Date;


public class User {

  private String id;
  private String  username;
  private String  phone;
  private String  email;
  private String  password;
  private String  confirmPassword;
  private Date    createTime;
  private Integer enable;
  @JSONField(deserialize=false,serialize=false)
  private String newPassword;
  private String token;

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Integer getEnable() {
    return enable;
  }

  public void setEnable(Integer enable) {
    this.enable = enable;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
