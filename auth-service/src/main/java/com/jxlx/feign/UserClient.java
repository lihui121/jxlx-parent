package com.jxlx.feign;

import com.jxlx.api.service.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("user-service")
public interface UserClient extends UserApi{
}
