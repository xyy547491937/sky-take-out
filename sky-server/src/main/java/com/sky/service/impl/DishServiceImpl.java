package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<Dish> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Dish getDishById(Integer id) {
       Dish dish = dishMapper.getDishById(id);
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
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.insert(dish);

        // 遍历每一个list
        List<DishFlavor> flavors = dishDTO.getFlavors();

        if(flavors!=null && flavors.size()!=0) {
            for (int i = 0; i < flavors.size(); i++) {
                DishFlavor f = flavors.get(i);
                f.setDishId(dish.getId());
                log.info("获取到的菜品id{}",dish.getId());
                dishFlavorMapper.insert(f);

            }
        }

    }
}
