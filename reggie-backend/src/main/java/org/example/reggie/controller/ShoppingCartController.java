package org.example.reggie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.example.reggie.entity.ShoppingCart;
import org.example.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "购物车接口")
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(summary = "列表查询用户购物车项目")
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        return R.success(shoppingCartService.listUserShoppingCart(BaseContext.getCurrentUserId()));
    }

    @Operation(summary = "增加购物车项目")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody ShoppingCart shoppingCart) {
        return R.success(shoppingCartService.addUserShoppingCart(BaseContext.getCurrentUserId(), shoppingCart));
    }

    @Operation(summary = "减少购物车项目")
    @PostMapping("/sub")
    public R<Boolean> sub(@RequestBody ShoppingCart shoppingCart) {
        return R.success(shoppingCartService.subUserShoppingCart(BaseContext.getCurrentUserId(), shoppingCart));
    }

    @Operation(summary = "清空购物车项目")
    @DeleteMapping("/clean")
    public R<Boolean> clean() {
        return R.success(shoppingCartService.cleanUserShoppingCart(BaseContext.getCurrentUserId()));
    }
}

