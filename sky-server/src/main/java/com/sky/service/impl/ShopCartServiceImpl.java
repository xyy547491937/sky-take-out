package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.ShoppingCartDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.DishFlavor;
import com.sky.entity.ShoppingCart;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.*;
import com.sky.properties.WeChatProperties;
import com.sky.service.ShopCartService;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    private ShopCartMapper shopCartMapper;

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Transactional
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //判断传来的是 菜品id 还是套餐id

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        // 获取用户id
        Long userId = BaseContext.getCurrentId();

        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shopCartMapper.list(shoppingCart);

        if (list != null && list.size() > 0) {
            for (ShoppingCart cart : list) {
                // 更新数量++
                Integer number = cart.getNumber();
                number++;
                cart.setNumber(number);
                shopCartMapper.update(cart);
            }

        } else {
            // 新增操作
            // 需要 判断 新增套餐还是菜品
            Long dishId = shoppingCartDTO.getDishId();
            Long setmealId = shoppingCartDTO.getSetmealId();
            if (dishId != null) {
                // 有值
                //根据菜品 id 查询 菜品信息

                DishVO dishById = dishMapper.getDishById(dishId);
                // 设置价格
                shoppingCart.setAmount(dishById.getPrice());
                // 设置名字

                shoppingCart.setName(dishById.getName());

                shoppingCart.setNumber(1);

                shoppingCart.setImage(dishById.getImage());

            }

            if (setmealId != null) {
                //查询套餐的详情

                SetmealDTO setmel = setmealMapper.getSetmelById(setmealId);

                shoppingCart = ShoppingCart.builder().amount(setmel.getPrice())
                        .image(setmel.getImage()).name(setmel.getName())
                        .number(1).build();

            }

            shoppingCart.setCreateTime(LocalDateTime.now());
            shopCartMapper.insert(shoppingCart);
        }
        // 判断商品是否已经存在  存在 +1 不存在 insert

    }

    @Override
    public List<ShoppingCart> getCart() {
        List<ShoppingCart> list = shopCartMapper.list(new ShoppingCart());
        return list;
    }

    /*
     * 删除指定一个
     * */
    @Transactional
    @Override
    public void delete(ShoppingCartDTO shoppingCartDTO) {

        // 删除购物车的指定id
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());

        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        // 删除 需要查询数据库是否为一条当前数据 --

        List<ShoppingCart> list = shopCartMapper.list(shoppingCart);
        // 肯定只有一条
        Integer number = list.get(0).getNumber();

        // 判断 number 是否等于1 等于1 删除  大于1 更新 --

        if (number == 1) {
            shopCartMapper.delete(shoppingCart);
        } else {
            number --;
            shoppingCart.setNumber(number);
            shopCartMapper.update(shoppingCart);

        }
    }

    /*
     *
     * 删除全部*/
    @Override
    public void deleteAll() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shopCartMapper.delete(shoppingCart);
    }
}
