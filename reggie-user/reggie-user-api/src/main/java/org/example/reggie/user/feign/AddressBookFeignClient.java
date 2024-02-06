package org.example.reggie.user.feign;

import org.example.reggie.user.entity.AddressBook;
import org.example.reggie.user.feign.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "reggie-user", configuration = DefaultFeignConfig.class)
public interface AddressBookFeignClient {

    String BASE_URL = "/feign/addressBook";

    @GetMapping(BASE_URL + "/{id}")
    AddressBook getById(@PathVariable Long id);
}
