package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {


    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    Dish getDishById(Integer id);

    void insert(DishDTO dishDTO);
/*
* 刪除
* */
    void delete(List<Long> ids);
}
