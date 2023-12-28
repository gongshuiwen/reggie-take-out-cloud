package org.example.reggie.security.handler;

import com.alibaba.fastjson.JSON;
import org.example.reggie.common.protocal.R;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JSON.toJSONString(R.error("权限验证失败，无当前资源的访问权限！")));
    }
}
