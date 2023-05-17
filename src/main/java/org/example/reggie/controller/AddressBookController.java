package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.example.reggie.entity.AddressBook;
import org.example.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id) {
        return R.success(addressBookService.getById(id));
    }

    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentUserId());
        return R.success(addressBookService.list(wrapper));
    }

    @PostMapping
    public R<String> create(@RequestBody AddressBook addressBook) {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentUserId());
        addressBook.setIsDefault(addressBookService.count(wrapper) == 0);
        addressBook.setUserId(BaseContext.getCurrentUserId());
        addressBookService.save(addressBook);
        return R.success("");
    }

    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentUserId());
        addressBookService.updateById(addressBook);
        return R.success("");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        addressBookService.removeByIds(ids);
        return R.success("");
    }

    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentUserId());
        wrapper.eq(AddressBook::getIsDefault, 1);
        return R.success(addressBookService.getOne(wrapper));
    }

    @PutMapping("/default")
    public R<String> setDefault(@RequestBody AddressBook addressBook) {
        addressBookService.changeUserDefaultAddressBook(BaseContext.getCurrentUserId(), addressBook.getId());
        return R.success("");
    }
}
