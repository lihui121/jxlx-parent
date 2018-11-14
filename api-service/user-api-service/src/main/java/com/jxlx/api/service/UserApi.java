package com.jxlx.api.service;

import com.jxlx.base.ResponseBase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
public interface UserApi {
    @PostMapping("/login")
    public ResponseBase login(@RequestParam("username")String username,@RequestParam("password") String password);
    @GetMapping("findUserById")
    public ResponseBase findUserById(@RequestParam("id")Long id);
}
