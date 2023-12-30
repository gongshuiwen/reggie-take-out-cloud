package org.example.reggie.common.interceptors;

import org.example.reggie.common.context.UserInfoContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserInfoInterceptor implements HandlerInterceptor {

    public static final String USER_INFO_HEADER = "UID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader(USER_INFO_HEADER);
        if (StringUtils.hasLength(userId)) {
            UserInfoContext.set(Long.valueOf(userId));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoContext.remove();
    }
}
