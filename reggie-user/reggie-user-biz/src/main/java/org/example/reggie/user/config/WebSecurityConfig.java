package org.example.reggie.user.config;

import org.example.reggie.common.security.CustomAccessDeniedHandler;
import org.example.reggie.common.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable csrf token validation for api
                .csrf().disable()
                .formLogin().disable()
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .mvcMatchers("/v3/api-docs/**").permitAll()
                                .mvcMatchers("/doc.html").permitAll()
                                .mvcMatchers("/webjars/**").permitAll()
                                .mvcMatchers("/rpc/**").permitAll()
                                .mvcMatchers(HttpMethod.POST, "/user/sendMsgCode/**").permitAll()
                                .anyRequest().authenticated()
                )
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions().sameOrigin())
                .sessionManagement(Customizer.withDefaults())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                .accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }
}
