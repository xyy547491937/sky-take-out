package com.sky.controller.user;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserDishController")
@Slf4j
@Api(tags = "c端菜品相关接口")
@RequestMapping("/user/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("菜品分页查询")
    @GetMapping("/list")
    public Result<List<DishVO>> page(@RequestParam Integer categoryId) {
        List<DishVO> result = dishService.getDishByCid(categoryId);
        return Result.success(result);
    }


}
