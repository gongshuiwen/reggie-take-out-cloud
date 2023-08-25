package org.example.reggie.config;

import com.alibaba.fastjson.JSON;
import org.example.reggie.common.R;
import org.example.reggie.security.*;
import org.example.reggie.security.handler.CustomAuthenticationFailureHandler;
import org.example.reggie.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.util.DigestUtils;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            MsgCodeAuthenticationProvider msgCodeAuthenticationProvider,
            DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter();
        filter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());

        http
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .mvcMatchers(HttpMethod.GET, "/employee/page").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.POST, "/employee").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.PUT, "/employee").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.DELETE, "/employee").hasRole("ADMIN")
                                .mvcMatchers("/front/**").permitAll()
                                .mvcMatchers("/backend/**").permitAll()
                                .mvcMatchers("/doc.html").permitAll()
                                .mvcMatchers("/webjars/**").permitAll()
                                .mvcMatchers("/swagger-resources").permitAll()
                                .mvcMatchers("/v2/api-docs").permitAll()
                                .mvcMatchers("/user/sendMsgCode").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf().disable()
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer
                                .logoutRequestMatcher(
                                        new OrRequestMatcher(
                                            new AntPathRequestMatcher("/employee/logout", "POST"),
                                            new AntPathRequestMatcher("/user/logout", "POST"))))
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions().sameOrigin())
                .authenticationProvider(msgCodeAuthenticationProvider)
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(Customizer.withDefaults())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setContentType("application/json;charset=UTF-8");
                                    response.getWriter().write(JSON.toJSONString(R.error("权限错误")));
                                }));
        SecurityFilterChain securityFilterChain = http.build();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        return securityFilterChain;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            PasswordEncoder passwordEncoder, UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
