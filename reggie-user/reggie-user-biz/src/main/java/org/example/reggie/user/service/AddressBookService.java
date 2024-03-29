package org.example.reggie.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.user.entity.AddressBook;

import java.util.List;

public interface AddressBookService extends IService<AddressBook> {

    AddressBook saveWithUserId(Long userId, AddressBook addressBook);

    List<AddressBook> listByUserId(Long userId);

    AddressBook getUserDefaultAddressBook(Long userId);

    Boolean setUserDefaultAddressBook(Long userId, Long id);
}
