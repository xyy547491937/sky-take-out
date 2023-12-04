package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("菜品分页查询")
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
    @ApiOperation("新增菜品")
    @PostMapping
    public Result insert(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品数据为{}",dishDTO);
        dishService.insert(dishDTO);
        return Result.success("新增成功");
    }

    /*
    * 刪除菜品
    * */


    @ApiOperation("刪除菜品")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        log.info("刪除菜品{}",ids);
        dishService.delete(ids);
       return  Result.success("删除成功");
    }
}
