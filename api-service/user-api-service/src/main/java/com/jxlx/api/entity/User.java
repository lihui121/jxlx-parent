package com.jxlx.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String username;// 用户名
    private String password;// 密码
    private String phone;// 电话
    private Date creatDate;// 创建时间
}
