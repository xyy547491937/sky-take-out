package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.LoginFailedException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.properties.WeChatProperties;
import com.sky.service.AddressBookService;
import com.sky.service.OrderService;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShopCartMapper shopCartMapper;

    @Transactional
    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        // 各种一场 地址簿为空

        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());

        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        //判断购物车是否为空
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> cartList = shopCartMapper.list(shoppingCart);

        if (cartList == null && cartList.size() > 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //  订单表插入一条
        Orders orders = new Orders();

        //copy



        orders = Orders.builder().orderTime(LocalDateTime.now())
                .payStatus(Orders.UN_PAID)
                .status(Orders.PENDING_PAYMENT)
                .number(String.valueOf(System.currentTimeMillis()))
                .phone(addressBook.getPhone())
                .consignee(addressBook.getConsignee()) //收货人
                .userId(BaseContext.getCurrentId())

                .build();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orderMapper.insert(orders);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        // 订单明细表 插入n 条
        for (ShoppingCart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();

            BeanUtils.copyProperties(cart,orderDetail);

            orderDetail.setOrderId(orders.getId());

            orderDetailList.add(orderDetail);

        }

        orderDetailMapper.insertBatch(orderDetailList);

        // 清空购物车

        shopCartMapper.delete(shoppingCart);

        OrderSubmitVO build = OrderSubmitVO.builder().id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .build();

        return build;
    }
}
