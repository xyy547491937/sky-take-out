package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /*
     * 根据分类id 菜品  状态查询
     * */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id =#{id}")
    Dish getDishById(Integer id);


    /* 新增菜品*/
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into dish( name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) " +
            "values(#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")

    void insert(Dish dish);



    void delete(List<Long> ids);
}
