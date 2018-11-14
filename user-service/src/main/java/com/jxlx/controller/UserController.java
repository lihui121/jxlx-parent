package com.jxlx.controller;


import com.jxlx.Feign.UserFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
    /*@Value("${pool}")
    private String ho;
    @Value("${server.port}")
    private String port;*/
    @Autowired
    private UserFeign userFeign;
    @GetMapping("/{id}")
   // @HystrixCommand(fallbackMethod = "fallback")
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
}
