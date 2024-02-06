package org.example.reggie.user.feign;

import org.example.reggie.user.entity.User;
import org.example.reggie.user.feign.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "reggie-user", configuration = DefaultFeignConfig.class)
public interface UserFeignClient {

    String BASE_URL = "/feign/user";

    @GetMapping(BASE_URL + "/{id}")
    User getById(@PathVariable Long id);

    @GetMapping(BASE_URL)
    User selectByPhone(@RequestParam String phone);

    @PostMapping(BASE_URL + "/register")
    User registerByPhone(@RequestParam String phone);
}
