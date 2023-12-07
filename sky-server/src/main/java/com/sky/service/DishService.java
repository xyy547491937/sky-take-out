package com.sky.service;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {


    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    DishVO getDishById(Long id);

    void insert(DishDTO dishDTO);
/*
* 刪除
* */
    void delete(List<Long> ids);

/*
* 修改菜品
* */
    void update(DishDTO dishDTO);

    /*
    * c端 根据分类id 查询菜品信息
    * */
    List<DishVO> getDishByCid(Integer categoryId);
}
