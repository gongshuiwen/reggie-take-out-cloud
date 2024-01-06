package org.example.reggie.user.feign;

import org.example.reggie.user.entity.User;
import org.example.reggie.user.feign.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "reggie-user", configuration = DefaultFeignConfig.class)
public interface UserFeignClient {

    @GetMapping("/rpc/user/{id}")
    User getById(@PathVariable Long id);

    @GetMapping("/rpc/user")
    User selectByPhone(@RequestParam String phone);

    @PostMapping("/rpc/user/register")
    User registerByPhone(@RequestParam String phone);
}
