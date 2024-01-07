package org.example.reggie.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.context.UserInfoContext;
import org.example.reggie.common.protocal.R;
import org.example.reggie.user.entity.AddressBook;
import org.example.reggie.user.service.AddressBookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "地址簿接口")
@RequestMapping("/addressBook")
public class AddressBookController {

    private final AddressBookService addressBookService;

    @Operation(summary = "查询地址簿信息")
    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id) {
        return R.success(addressBookService.getById(id));
    }

    @Operation(summary = "创建地址簿信息")
    @PostMapping
    public R<AddressBook> create(@RequestBody AddressBook addressBook) {
        return R.success(addressBookService.saveWithUserId(UserInfoContext.get(), addressBook));
    }

    @Operation(summary = "更新地址簿信息")
    @PutMapping("/{id}")
    public R<Boolean> update(@PathVariable Long id, @RequestBody AddressBook addressBook) {
        addressBook.setId(id);
        return R.success(addressBookService.updateById(addressBook));
    }

    @Operation(summary = "删除地址簿信息")
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Long id){
        return R.success(addressBookService.removeById(id));
    }

    @Operation(summary = "列表查询地址簿信息")
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        return R.success(addressBookService.listByUserId(UserInfoContext.get()));
    }

    @Operation(summary = "查询用户默认地址簿")
    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        return R.success(addressBookService.getUserDefaultAddressBook(UserInfoContext.get()));
    }

    @Operation(summary = "更新用户默认地址簿")
    @PutMapping("/default")
    public R<Boolean> setDefault(@RequestBody AddressBook addressBook) {
        return R.success(addressBookService.setUserDefaultAddressBook(UserInfoContext.get(), addressBook.getId()));
    }
}
