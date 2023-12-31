package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /*
     * 新增口味表
     * */
    @Insert("insert into dish_flavor( dish_id, name, value) VALUES (#{dishId},#{name},#{value})")
    void insert(DishFlavor dishFlavor);

    /*
     * 删除
     * */
    void delete(List<Long> ids);

    /*
    * 根据菜品id 获取口味的所有信息
    * */
    @Select("select * from dish_flavor where dish_id =#{id}")
    List<DishFlavor> getDishById(Long id);
}
