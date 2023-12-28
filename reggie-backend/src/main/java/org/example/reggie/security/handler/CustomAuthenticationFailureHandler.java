package org.example.reggie.security.handler;

import com.alibaba.fastjson.JSON;
import org.example.reggie.common.protocal.R;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JSON.toJSONString(R.error(exception.getMessage())));
    }
}
