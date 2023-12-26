package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {

    List<ShoppingCart> listUserShoppingCart(Long userId);

    Boolean addUserShoppingCart(Long userId, ShoppingCart shoppingCart);

    Boolean subUserShoppingCart(Long userId, ShoppingCart shoppingCart);

    Boolean cleanUserShoppingCart(Long userId);
}
