package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper {

    /*
    * 新增口味表
    * */
    @Insert("insert into dish_flavor( dish_id, name, value) VALUES (#{dishId},#{name},#{value})")
    void insert(DishFlavor dishFlavor);
}