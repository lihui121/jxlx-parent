package com.jxlx.controller;

import com.jxlx.Feign.UserFeign;
import com.jxlx.common.RestResponse;
import com.jxlx.domain.User;
import com.jxlx.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
//这是一个测试
@RestController
@RequestMapping("user")
public class UserController {
    /*@Value("${pool}")  s
    private String ho;
    @Value("${server.port}")
    private String port;*/
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserFeign userFeign;

    @ApiOperation(value = "这是一个测试接口",notes="根据输入值来获取")
    @GetMapping("/{id}")
    public String hello(@PathVariable("id") String id){
        String office = userFeign.findById(id);
        return "你好你的端口是你输入是"+id+office;
    }


    @GetMapping("/toOffice/{id}")
    public String toOffice(@PathVariable("id") String id){
        return "你好用户返回"+id;
    }

   @Autowired
   private RestTemplate restTemplate;
    @GetMapping(value = "ribbon/get")
    @HystrixCommand(fallbackMethod = "fallback")
    public String ribbonGet() {
        return restTemplate.getForEntity("http://localhost:8690/office/{1111}", String.class, "client_ribbon").getBody();
    }

    public String fallback() {
        String temp = "testHystrix调用失败!" ;
        System.out.println(temp);
        return temp;
    }


    //-------------------查询---------------------

    @RequestMapping("getById")
    public RestResponse<User> getUserById(Long id){
        User user = userService.getUserById(id);
        return RestResponse.success(user);
    }

    @RequestMapping("getList")
    public RestResponse<List<User>> getUserList(@RequestBody User user){
        List<User> users = userService.getUserByQuery(user);
        return RestResponse.success(users);
    }


    //----------------------注册----------------------------------
    @RequestMapping("add")
    public RestResponse<User> add(@RequestBody User user){
        userService.addAccount(user);
        return RestResponse.success();
    }

    /**
     * 主要激活key的验证
     */
    @RequestMapping("enable")
    public RestResponse<Object> enable(String key){
        userService.enable(key);
        return RestResponse.success();
    }

    //------------------------登录/鉴权--------------------------

    @RequestMapping("auth")
    public RestResponse<User> auth(@RequestBody User user){
        User finalUser = userService.auth(user.getUsername(),user.getPassword());
        return RestResponse.success(finalUser);
    }


    @RequestMapping("get")
    public RestResponse<User> getUser(String token){
        User finalUser = userService.getLoginedUserByToken(token);
        return RestResponse.success(finalUser);
    }

    @RequestMapping("logout")
    public RestResponse<Object> logout(String token){
        userService.invalidate(token);
        return RestResponse.success();
    }

    @RequestMapping("update")
    public RestResponse<User> update(@RequestBody User user){
        User updateUser = userService.updateUser(user);
        return RestResponse.success(updateUser);
    }

    @RequestMapping("reset")
    public RestResponse<User> reset(String key ,String password){
        User updateUser = userService.reset(key,password);
        return RestResponse.success(updateUser);
    }

    @RequestMapping("resetNotify")
    public RestResponse<User> resetNotify(String email,String url){
//        userService.resetNotify(email,url);
        return RestResponse.success();
    }

}
