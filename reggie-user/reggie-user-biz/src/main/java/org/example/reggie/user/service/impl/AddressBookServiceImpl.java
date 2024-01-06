package org.example.reggie.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.user.entity.AddressBook;
import org.example.reggie.user.mapper.AddressBookMapper;
import org.example.reggie.user.service.AddressBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    @Override
    public AddressBook saveWithUserId(Long userId, AddressBook addressBook) {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, userId);
        addressBook.setIsDefault(count(wrapper) == 0);
        addressBook.setUserId(userId);
        this.save(addressBook);
        return addressBook;
    }

    @Override
    public List<AddressBook> listByUserId(Long userId) {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, userId);
        return this.list(wrapper);
    }

    @Override
    public AddressBook getUserDefaultAddressBook(Long userId) {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, userId);
        wrapper.eq(AddressBook::getIsDefault, 1);
        return this.getOne(wrapper);
    }

    @Override
    @Transactional
    public Boolean setUserDefaultAddressBook(Long userId, Long id) {
        // 取消旧的默认地址
        this.update(new LambdaUpdateWrapper<AddressBook>()
                .set(AddressBook::getIsDefault, Boolean.FALSE)
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDefault, Boolean.TRUE));

        // 设置新的默认地址
        this.update(new LambdaUpdateWrapper<AddressBook>()
                .set(AddressBook::getIsDefault, Boolean.TRUE)
                .eq(AddressBook::getId, id)
                .eq(AddressBook::getIsDefault, Boolean.FALSE));

        return Boolean.TRUE;
    }
}
