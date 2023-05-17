package org.example.reggie.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.reggie.common.R;
import org.example.reggie.entity.User;
import org.example.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.stereotype.Component;

@Component
public class MsgCodeAuthenticationProvider implements AuthenticationProvider {

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = (String) authentication.getPrincipal();
        String code = (String) authentication.getCredentials();
        String validationCode = stringRedisTemplate.opsForValue().get("msgCode:" + phone);
        if (validationCode == null || !validationCode.equals(code)) {
            throw new AuthenticationServiceException("验证码失效或不正确！");
        }

        stringRedisTemplate.delete("msgCode:" + phone);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        User user = userService.getOne(wrapper);
        if (user == null) {
            user = new User();
            user.setStatus(1);
            user.setPhone(phone);
            userService.save(user);
        } else if (user.getStatus() == 0) {
            throw new DisabledException("登录失败，用户已被禁用！");
        }
        MsgCodeAuthenticationToken authenticatedToken = MsgCodeAuthenticationToken
                .authenticated(phone, code, this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        authenticatedToken.setDetails(user);
        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MsgCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }
}
