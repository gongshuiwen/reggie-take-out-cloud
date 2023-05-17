package org.example.reggie.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private static final RequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER
            = new OrRequestMatcher(
                    new AntPathRequestMatcher("/employee/login", "POST"),
                    new AntPathRequestMatcher("/user/login", "POST"));

    public CustomAuthenticationProcessingFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (request.getMethod().equals("POST") && (
                Objects.equals(request.getContentType(), MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                Objects.equals(request.getContentType(), MediaType.APPLICATION_JSON_VALUE))) {

            if (request.getRequestURI().equals("/employee/login")) {
                Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                String username = userInfo.get(SPRING_SECURITY_FORM_USERNAME_KEY);
                String password = userInfo.get(SPRING_SECURITY_FORM_PASSWORD_KEY);
                UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } else if (request.getRequestURI().equals("/user/login")) {
                Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                String phone = userInfo.get("phone");
                String code = userInfo.get("code");
                MsgCodeAuthenticationToken authRequest = MsgCodeAuthenticationToken.unauthenticated(phone, code);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }

        throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
