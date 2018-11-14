package com.jxlx.Feign;

import org.springframework.stereotype.Component;

@Component
public class UserFeignImpl implements UserFeign {
    @Override
    public String findById (String id){
        return "查询失败了";
    };
}
