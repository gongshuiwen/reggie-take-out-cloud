package org.example.reggie.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.protocal.R;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "用户接口")
@RequestMapping("/user")
public class UserController {

    private final StringRedisTemplate stringRedisTemplate;

    @Operation(summary = "发送验证码")
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