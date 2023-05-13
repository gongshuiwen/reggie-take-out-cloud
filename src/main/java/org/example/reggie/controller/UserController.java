package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.User;
import org.example.reggie.form.LoginForm;
import org.example.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody LoginForm loginForm) {
        String phone = loginForm.getPhone();
        String validationCode = stringRedisTemplate.opsForValue().get("msgCode:" + phone);
        if (validationCode == null || !validationCode.equals(loginForm.getCode())) {
            return R.error("验证码失效或不正确！");
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
            return R.error("登录失败，用户已被禁用！");
        }

        request.getSession().setAttribute("userId", user.getId());
        return R.success(user);
    }

    @PostMapping("/loginout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("userId");
        return R.success("退出成功！");
    }

    @PostMapping("/sendMsgCode")
    public R<String> sendMsgCode(@RequestBody User userdto) {

        // 生成验证码
        String validationCode = "1234";

        // 发送验证码（异步执行）

        // 写入 redis
        stringRedisTemplate.opsForValue().set("msgCode:" + userdto.getPhone(), validationCode, 5, TimeUnit.MINUTES);
        return R.success("验证码发送成功！");
    }
}
