package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShopCartService {

    void add(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> getCart();

    void delete(ShoppingCartDTO shoppingCartDTO);

    void deleteAll();
}
