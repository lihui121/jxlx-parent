package com.jxlx.Feign;

import org.springframework.stereotype.Component;

@Component
public class OfficeFeignImpl implements OfficeFeign {

    public String findUser(String id){
        return "机构调用用户失败了";
    }
}
