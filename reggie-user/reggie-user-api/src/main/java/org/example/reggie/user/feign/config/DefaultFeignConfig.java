package org.example.reggie.user.feign.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.example.reggie.common.context.UserInfoContext;
import org.example.reggie.common.interceptors.UserInfoInterceptor;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {

    @Bean
    Logger.Level level() {
        return Logger.Level.FULL;
    }

    @Bean
    RequestInterceptor requestInterceptor() {
        return template -> {
            Long userId = UserInfoContext.get();
            if (userId != null) {
                template.header(UserInfoInterceptor.USER_INFO_HEADER, userId.toString());
            }
        };
    }
}
