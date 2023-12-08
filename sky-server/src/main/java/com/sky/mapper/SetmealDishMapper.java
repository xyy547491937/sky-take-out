package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    @Insert("insert into setmeal_dish(setmeal_id, dish_id, name, price, copies) values " +
            "( #{setmealId},#{dishId},#{name},#{price},#{copies})")
    void insert(SetmealDish setmealDish);

    @Select("select * from setmeal_dish where setmeal_id =#{id}")
    List<SetmealDish> setmealWithDish(Long id);


    /*
    * 更新套餐菜品
    * */
    void update(SetmealDish setmealDish);
}
