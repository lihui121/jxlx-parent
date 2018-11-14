package com.jxlx.service.impl;


import com.jxlx.api.entity.User;
import com.jxlx.api.entity.UserInfo;
import com.jxlx.api.service.AuthApi;
import com.jxlx.api.utils.JwtUtils;
import com.jxlx.base.BaseApiService;
import com.jxlx.base.ResponseBase;
import com.jxlx.config.JwtProperties;
import com.jxlx.constants.Constants;
import com.jxlx.feign.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Qin PengCheng
 * @date 2018/6/13
 */
@RestController("/auth")
//@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService extends BaseApiService implements AuthApi {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    /**
     *获取令牌的方法
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/getToken")
    public ResponseBase getToken(@RequestParam("username")String username, @RequestParam("password")String password) {
        try {
           /* ResponseBase userResponseEntity = this.userClient.login(username, password);
            if (userResponseEntity.getData()==null) {
                logger.info("用户信息不存在，{}", username);
                return setResultError(Constants.HTTP_RES_CODE_201,"用户名密码错误");
            }
            User user = (User) userResponseEntity.getData();*/
            //生成令牌
            User user=new User();
            user.setId((long)1111);
            user.setUsername("lh");
            user.setPassword("123456");
            UserInfo userInfo = new UserInfo((long)1111,"lih");
            BeanUtils.copyProperties(user, userInfo);
            String token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return setResultSuccess(token);
        } catch (Exception e) {
            logger.error("生成令牌的过程中出错");
            return setResultError(201,"令牌生成错误");
        }
    }

   // @Override
    @PostMapping("/findUserByToken")
    public ResponseBase findUserByToken(@RequestParam("token")String token) {
        UserInfo userInfo=null;
        try {
            userInfo= JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            if(userInfo==null){
                return setResultError(403,"token有误,请重新登录");
            }

             /*ResponseBase userById = userClient.findUserById(userInfo.getId());
            if(userById.getData()==null){
                return setResultError(403,"token有误,请重新登录");
             }*/
        } catch (Exception e) {
            e.printStackTrace();
                return setResultError(403,"系统内部错误");
        }
        return setResultSuccess(userInfo);
    }
}
