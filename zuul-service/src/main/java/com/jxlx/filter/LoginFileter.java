package com.jxlx.filter;


import com.jxlx.api.entity.UserInfo;
import com.jxlx.api.utils.JwtUtils;

import com.jxlx.feign.AuthClient;
import com.jxlx.utils.CookieUtils;
import com.jxlx.zuul.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;


public class LoginFileter extends ZuulFilter {
    @Autowired
    AuthClient authClient;
    @Autowired
    private JwtProperties properties;

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }
    //拦截器 验证是否登录
    @Override
    public Object run() throws ZuulException {
        //先获取token
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        String token = CookieUtils.getCookieValue(request,"LY_TOKEN");
       // String token="eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MTExMSwibmFtZSI6ImxoIiwiZXhwIjoxNTQxNDAzNjQ5fQ.RVliJUTzzrhnqafVgrDw1JfEo4pYff-L0A_tKtxlPBUieVIfiSAM_5qWl06qsA1tO2XXO0cHjaD1OIqsJ-3zXGh7sfrbjcoZ184bTRSAes1eZYXEpEvDrqY8Imk75Td3umhp3eRenaqU4Tcjp_jR-hwC0ySZDwWt7A_A8JKHYJo";
       /* // 如果没有token 则认为没有登录 返回403 跳转到登录界面
        if(StringUtils.isEmpty(token)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
        // 如果redis中没有token 则认为没有登录 返回2403 跳转到登录页面
        BaseRedisService baseRedisService = new BaseRedisService();
        Object string = baseRedisService.getString(token);
        if(string==null){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }*/
        try {
            // 校验通过什么都不做
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(token, this.properties.getPublicKey());
            System.out.println(infoFromToken.getId()+":"+infoFromToken.getUsername());
        } catch (Exception e) {
            // 校验出现异常，返回403
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
        return null;

    }
}
