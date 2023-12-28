package org.example.reggie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.protocal.R;
import org.example.reggie.entity.AddressBook;
import org.example.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "地址簿接口")
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @Operation(summary = "查询单个地址簿信息")
    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id) {
        return R.success(addressBookService.getById(id));
    }

    @Operation(summary = "创建地址簿信息")
    @PostMapping
    public R<AddressBook> create(@RequestBody AddressBook addressBook) {
        return R.success(addressBookService.saveWithUserId(BaseContext.getCurrentUserId(), addressBook));
    }

    @Operation(summary = "更新地址簿信息")
    @PutMapping
    public R<Boolean> update(@RequestBody AddressBook addressBook) {
        return R.success(addressBookService.updateById(addressBook));
    }

    @Operation(summary = "删除地址簿信息")
    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids){
        return R.success(addressBookService.removeByIds(ids));
    }

    @Operation(summary = "列表查询地址簿信息")
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        return R.success(addressBookService.listByUserId(BaseContext.getCurrentUserId()));
    }

    @Operation(summary = "查询用户默认地址簿")
    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        return R.success(addressBookService.getUserDefaultAddressBook(BaseContext.getCurrentUserId()));
    }

    @Operation(summary = "更新用户默认地址簿")
    @PutMapping("/default")
    public R<Boolean> setDefault(@RequestBody AddressBook addressBook) {
        return R.success(addressBookService.setUserDefaultAddressBook(BaseContext.getCurrentUserId(), addressBook.getId()));
    }
}
