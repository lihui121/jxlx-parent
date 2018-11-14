package com.jxlx.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user-service",fallback = OfficeFeignImpl.class)
public interface OfficeFeign {
    @RequestMapping("/toOffice/{id}")
    public String findUser(@PathVariable("id") String id);
}
