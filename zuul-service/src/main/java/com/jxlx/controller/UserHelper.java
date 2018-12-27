package com.jxlx.controller;

import com.google.common.base.Objects;
import com.google.common.collect.Range;
import com.jxlx.common.ResultMsg;
import com.jxlx.model.User;
import org.apache.commons.lang3.StringUtils;

public class UserHelper {
  
  public static ResultMsg validateResetPassword(String key, String password, String confirmPassword) {
    if (StringUtils.isBlank(key) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
      return ResultMsg.errorMsg("参数有误");
    }
    if (!Objects.equal(password, confirmPassword)) {
      return ResultMsg.errorMsg("密码必须与确认密码一致");
    }
    return ResultMsg.success();
  }

  public static ResultMsg validate(User account) {
    if (StringUtils.isBlank(account.getEmail())) {
      return ResultMsg.errorMsg("Email有误");
    }
    if (StringUtils.isBlank(account.getUsername())) {
      return ResultMsg.errorMsg("名字有误");
    }
    if (StringUtils.isBlank(account.getConfirmPasswd()) || StringUtils.isBlank(account.getPassword()) || !account.getPassword().equals(account.getConfirmPasswd())) {
      return ResultMsg.errorMsg("密码有误");
    }
    if (account.getPassword().length() < 6){
      return ResultMsg.errorMsg("密码长度应大于6位");
    }
    return ResultMsg.success();
  }


}
