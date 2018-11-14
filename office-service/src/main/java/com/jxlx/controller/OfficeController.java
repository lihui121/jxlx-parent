package com.jxlx.controller;


import com.jxlx.Feign.OfficeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/office")
public class OfficeController {
    @Autowired
    private OfficeFeign officeFeign;

    @RequestMapping("{id}")
    public String findById(@PathVariable("id") String id) throws InterruptedException {

        return id;
    }

    @RequestMapping("/getUser/{id}")
    public  String findUser(@PathVariable("id") String id){
        return "用户名是"+officeFeign.findUser(id);
    }
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "ribbon/get")
    public String ribbonGet() {
        return restTemplate.getForEntity("http://localhost:8689/get?para={1111}", String.class, "client_ribbon").getBody();
    }

}
