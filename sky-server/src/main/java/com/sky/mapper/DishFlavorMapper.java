package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper {

    /*
    * 新增口味表
    * */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into dish_flavor( dish_id, name, value) VALUES (#{dishId},#{name},#{value})")
    void insert(DishFlavor dishFlavor);
}
