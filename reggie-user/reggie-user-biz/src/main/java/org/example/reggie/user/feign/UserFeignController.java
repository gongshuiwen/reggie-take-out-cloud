package org.example.reggie.user.feign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.user.entity.User;
import org.example.reggie.user.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UserFeignClient.BASE_URL)
public class UserFeignController implements UserFeignClient {

    private final UserService userService;

    @Override
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Override
    public User selectByPhone(@RequestParam String phone) {
        return userService.selectByPhone(phone);
    }

    @Override
    public User registerByPhone(@RequestParam String phone) {
        return userService.registerByPhone(phone);
    }
}
