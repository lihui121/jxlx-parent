package com.jxlx.feign;

import com.jxlx.api.service.AuthApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("auth-service")
public interface AuthClient extends AuthApi {
}
