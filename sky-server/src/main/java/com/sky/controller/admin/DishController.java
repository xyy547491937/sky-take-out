package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult result = dishService.page(dishPageQueryDTO);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<Dish> getDishById(@PathVariable Integer id) {
        log.info("根据菜品id 查询 菜品详情{}", id);
        Dish dish = dishService.getDishById(id);
        return Result.success(dish);
    }

    /*
    * 新增菜品
    * */
    @PostMapping
    public Result insert(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品数据为{}",dishDTO);
        dishService.insert(dishDTO);
        return Result.success("新增成功");
    }
}
