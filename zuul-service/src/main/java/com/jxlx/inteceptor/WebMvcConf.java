package com.jxlx.inteceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConf implements WebMvcConfigurer {

  @Autowired
  private AuthInterceptor authInterceptor;
  
  @Autowired
  private AuthActionInterceptor authActionInterceptor;
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
    //拦截需要登录的请求
    registry
        .addInterceptor(authActionInterceptor).addPathPatterns("/house/toAdd")
        .addPathPatterns("/accounts/profile").addPathPatterns("/accounts/profileSubmit")
        .addPathPatterns("/house/bookmarked").addPathPatterns("/house/del")
        .addPathPatterns("/house/ownlist").addPathPatterns("/house/add")
        .addPathPatterns("/house/toAdd").addPathPatterns("/agency/agentMsg")
        .addPathPatterns("/comment/leaveComment").addPathPatterns("/comment/leaveBlogComment");
    
//    super.addInterceptors(registry);
  }

}
