package com.jxlx.api.service;

import com.jxlx.base.ResponseBase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/auth")
public interface AuthApi {
    //用户首次登陆获取token
    @PostMapping("/getToken")
    public ResponseBase getToken(@RequestParam("username")String username, @RequestParam("password") String password);
    //用户再次登陆通过token 获取用户信息
    @PostMapping("/findUserByToken")
    public ResponseBase findUserByToken(@RequestParam("token") String token);


}
