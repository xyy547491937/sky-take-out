package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public DishVO getDishById(Long id) {
        DishVO dish = dishMapper.getDishById(id);

        log.info("获取的dish 数据为为{}", dish);

        // 在根据菜品id 获取 口味 信息
        List<DishFlavor> dishFlavors = dishFlavorMapper.getDishById(id);

        dish.setFlavors(dishFlavors);

        return dish;
    }

    /*
     * 新增菜品
     * */
    @Transactional
    @Override
    public void insert(DishDTO dishDTO) {
        Dish dish = new Dish();
        // 属性合并
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.insert(dish);

        // 遍历每一个list
        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors != null && flavors.size() != 0) {
            for (int i = 0; i < flavors.size(); i++) {
                DishFlavor f = flavors.get(i);
                f.setDishId(dish.getId());
                log.info("获取到的菜品id{}", dish.getId());
                dishFlavorMapper.insert(f);

            }
        }

    }

    @Transactional
    @Override
    public void delete(List<Long> ids) {

        //判断当前菜品是否能能够删除 起售中不能删除
        for (int i = 0; i < ids.size(); i++) {
            DishVO dish = dishMapper.getDishById(ids.get(i));
            if (dish.getStatus() == 1) {
                // 起售中 不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }


        }

        // 当前菜品是否被套餐关联，关联不能删除
        List<Long> setmealIds = setmealMapper.getSetmelIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 删除 菜品菜品
        dishMapper.delete(ids);
        // 删除口味
        dishFlavorMapper.delete(ids);

//        setmealMapper.delete(ids);
    }

    /*
     * 修改菜品
     * */
    @Override
    public void update(DishDTO dishDTO) {
        // 先删除 口味 在重新插入口味数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        log.info("copy 数据为{}", dish);
        dishMapper.update(dish);

        // 先删除
        List ids = new ArrayList<Long>();
        ids.add(dishDTO.getId());
        dishFlavorMapper.delete(ids);

        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishDTO.getId());
                dishFlavorMapper.insert(flavor);
            }
        }


    }

    @Override
    public List<DishVO> getDishByCid(Integer categoryId) {
        // 根据分类id 查询菜品 所有的信息
        List<DishVO> dishs = dishMapper.getDishByCid(categoryId);

        for (DishVO dish : dishs) {
            List<DishFlavor> flavors = dishFlavorMapper.getDishById(dish.getId());

            dish.setFlavors(flavors);

        }

        return dishs;
    }
}
