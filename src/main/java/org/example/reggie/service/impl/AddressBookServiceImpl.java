package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.entity.AddressBook;
import org.example.reggie.mapper.AddressBookMapper;
import org.example.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    @Override
    public void changeUserDefaultAddressBook(Long userId, Long id) {
        // 取消旧的默认地址
        this.update(new LambdaUpdateWrapper<AddressBook>()
                .set(AddressBook::getIsDefault, 0)
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDefault, 1));

        // 更新新的默认地址
        this.update(new LambdaUpdateWrapper<AddressBook>()
                .set(AddressBook::getIsDefault, 0)
                .eq(AddressBook::getId, id));
    }
}
