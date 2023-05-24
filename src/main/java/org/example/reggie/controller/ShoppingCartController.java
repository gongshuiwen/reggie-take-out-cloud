package org.example.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.example.reggie.entity.ShoppingCart;
import org.example.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        return R.success(shoppingCartService.listUserShoppingCart(BaseContext.getCurrentUserId()));
    }

    @PostMapping("/add")
    public R<Boolean> add(@RequestBody ShoppingCart shoppingCart) {
        return R.success(shoppingCartService.addUserShoppingCart(BaseContext.getCurrentUserId(), shoppingCart));
    }

    @PostMapping("/sub")
    public R<Boolean> sub(@RequestBody ShoppingCart shoppingCart) {
        return R.success(shoppingCartService.subUserShoppingCart(BaseContext.getCurrentUserId(), shoppingCart));
    }

    @DeleteMapping("/clean")
    public R<Boolean> clean() {
        return R.success(shoppingCartService.cleanUserShoppingCart(BaseContext.getCurrentUserId()));
    }
}

