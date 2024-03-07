package org.example.reggie.user.feign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.user.entity.AddressBook;
import org.example.reggie.user.service.AddressBookService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AddressBookFeignClient.BASE_URL)
public class AddressBookFeignController implements AddressBookFeignClient {

    private final AddressBookService addressBookService;

    @Override
    public AddressBook getById(@PathVariable Long id) {
        return addressBookService.getById(id);
    }
}
