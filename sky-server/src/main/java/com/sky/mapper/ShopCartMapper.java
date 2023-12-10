package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopCartMapper {
    /*
     * 查询列表
     * */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /*
     * 购物车添加
     * */
    void insert(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);

    void delete(ShoppingCart ShoppingCart);
}
