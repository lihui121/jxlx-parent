package com.jxlx.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "office-service",fallback = UserFeignImpl.class)
public interface UserFeign {
    @RequestMapping("/office/{id}")
    public String  findById(@PathVariable("id") String id);

}
