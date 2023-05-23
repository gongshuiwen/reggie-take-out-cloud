package org.example.reggie.controller;

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

    @PostMapping
    public R<AddressBook> create(@RequestBody AddressBook addressBook) {
        return R.success(addressBookService.saveWithUserId(BaseContext.getCurrentUserId(), addressBook));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody AddressBook addressBook) {
        return R.success(addressBookService.updateById(addressBook));
    }

    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids){
        return R.success(addressBookService.removeByIds(ids));
    }

    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        return R.success(addressBookService.listByUserId(BaseContext.getCurrentUserId()));
    }

    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        return R.success(addressBookService.getUserDefaultAddressBook(BaseContext.getCurrentUserId()));
    }

    @PutMapping("/default")
    public R<Boolean> setDefault(@RequestBody AddressBook addressBook) {
        return R.success(addressBookService.setUserDefaultAddressBook(BaseContext.getCurrentUserId(), addressBook.getId()));
    }
}
