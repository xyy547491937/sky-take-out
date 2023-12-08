package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);


    void delete(List<Long> ids);


    List<SetmealDTO> getSetmelIds(List<Long> ids);

    /*
    * 分页查询 套餐
    * */

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /*
    * 新增套餐
    * */
    @AutoFill(value = OperationType.INSERT)
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into setmeal (category_id, name, price,status, description, image, create_time, update_time, create_user, update_user) values(#{categoryId}," +
            "#{name},#{price},#{status},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Setmeal setmeal);

    @Select("select * from setmeal where id =#{id}")
    SetmealDTO getSetmelById(Long id);


    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);
}
