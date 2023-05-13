package org.example.reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    private final static AntPathMatcher matcher = new AntPathMatcher();
    private final static String[] allowedPaths = {
            "/employee/login",
            "/user/login",
            "/user/sendMsgCode",
            "/front/**",
            "/backend/**"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =  (HttpServletRequest) servletRequest;
        Long currentEmployeeId = (Long) request.getSession().getAttribute("employeeId");
        Long currentUserId = (Long) request.getSession().getAttribute("userId");

        if (!checkURI(request.getRequestURI()) && currentEmployeeId == null && currentUserId == null) {
            servletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return;
        }
        BaseContext.setCurrentEmployeeId(currentEmployeeId);
        BaseContext.setCurrentUserId(currentUserId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean checkURI(String path) {
        for (String allowedPath: allowedPaths) {
            if (matcher.match(allowedPath, path)) return true;
        }
        return false;
    }
}
