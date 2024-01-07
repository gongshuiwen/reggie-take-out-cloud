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
    @Transactional
    public AddressBook saveWithUserId(Long userId, AddressBook addressBook) {
        addressBook.setIsDefault(countByUserId(userId) == 0);
        addressBook.setUserId(userId);
        save(addressBook);
        return addressBook;
    }

    private long countByUserId(Long userId) {
        return count(new LambdaQueryWrapper<AddressBook>().eq(AddressBook::getUserId, userId));
    }

    @Override
    public List<AddressBook> listByUserId(Long userId) {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, userId);
        return list(wrapper);
    }

    @Override
    public AddressBook getUserDefaultAddressBook(Long userId) {
        return getOne(new LambdaQueryWrapper<AddressBook>()
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDefault, Boolean.TRUE));
    }

    @Override
    @Transactional
    public Boolean setUserDefaultAddressBook(Long userId, Long id) {
        // 取消旧的默认地址
        update(new LambdaUpdateWrapper<AddressBook>()
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDefault, Boolean.TRUE)
                .set(AddressBook::getIsDefault, Boolean.FALSE));

        // 设置新的默认地址
        update(new LambdaUpdateWrapper<AddressBook>()
                .eq(AddressBook::getId, id)
                .eq(AddressBook::getIsDefault, Boolean.FALSE)
                .set(AddressBook::getIsDefault, Boolean.TRUE));

        return Boolean.TRUE;
    }
}
