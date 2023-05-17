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
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sendMsgCode")
    public R<String> sendMsgCode(@RequestParam String phone) {

        // 生成验证码
        String validationCode = "1234";

        // 发送验证码（异步执行）

        // 写入 redis
        stringRedisTemplate.opsForValue().set("msgCode:" + phone, validationCode, 10, TimeUnit.MINUTES);
        return R.success("验证码发送成功！");
    }
}
