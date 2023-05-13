package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.AddressBook;
import org.springframework.transaction.annotation.Transactional;

public interface AddressBookService extends IService<AddressBook> {

    @Transactional
    public void changeUserDefaultAddressBook(Long userId, Long id);
}
