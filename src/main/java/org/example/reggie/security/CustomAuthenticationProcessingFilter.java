package org.example.reggie.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
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

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_PASSWORD_KEY = "password";

    private static final RequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER
            = new OrRequestMatcher(
            new AntPathRequestMatcher("/employee/login", "POST"),
            new AntPathRequestMatcher("/user/login", "POST"));
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public CustomAuthenticationProcessingFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication Http Method not supported: " + request.getMethod());
        }

        if (!request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException("Authentication ContentType not supported: " + request.getContentType());
        }

        Authentication authRequest = buildAuthentication(request);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private Authentication buildAuthentication(HttpServletRequest request) throws IOException {
        if (request.getRequestURI().equals("/employee/login")) {
            return buildUsernamePasswordAuthenticationToken(request);
        } else if (request.getRequestURI().equals("/user/login")) {
            return buildMsgCodeAuthenticationToken(request);
        }

        // should never be thrown, just for defensive
        throw new AuthenticationServiceException("Authentication Url not supported: " + request.getRequestURI());
    }

    @SuppressWarnings("unchecked")
    private UsernamePasswordAuthenticationToken buildUsernamePasswordAuthenticationToken(HttpServletRequest request) throws IOException {
        Map<String, String> userInfo = OBJECT_MAPPER.readValue(request.getInputStream(), Map.class);
        String username = userInfo.get(SPRING_SECURITY_USERNAME_KEY);
        String password = userInfo.get(SPRING_SECURITY_PASSWORD_KEY);
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        this.setDetails(request, authRequest);
        return authRequest;
    }

    @SuppressWarnings("unchecked")
    private MsgCodeAuthenticationToken buildMsgCodeAuthenticationToken(HttpServletRequest request) throws IOException {
        Map<String, String> userInfo = OBJECT_MAPPER.readValue(request.getInputStream(), Map.class);
        String phone = userInfo.get("phone");
        String code = userInfo.get("code");
        return MsgCodeAuthenticationToken.unauthenticated(phone, code);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
