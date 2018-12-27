package com.jxlx.model;


import java.util.Date;

public class User {
  
  private String id;
  private String  username;
  private String  phone;
  private String  email;
  private String  password;
  private String  confirmPasswd;
  private Date    createTime;
  private Integer enable;
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
  public String getConfirmPasswd() {
    return confirmPasswd;
  }
  public void setConfirmPasswd(String confirmPasswd) {
    this.confirmPasswd = confirmPasswd;
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
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
