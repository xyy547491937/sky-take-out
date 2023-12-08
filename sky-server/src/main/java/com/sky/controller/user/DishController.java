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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserDishController")
@Slf4j
@Api(tags = "c端菜品相关接口")
@RequestMapping("/user/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;
    @ApiOperation("菜品分页查询")
    @GetMapping("/list")
    public Result<List<DishVO>> page(@RequestParam Integer categoryId) {

        // 查询redis  是否有数据 dish_cid
        String key = "dish_" + categoryId;

        List<DishVO>  list = ( List<DishVO> )redisTemplate.opsForValue().get(key);

        if(list !=null && list.size()> 0) {
              return Result.success(list);
        }

        // 查询数据库
        List<DishVO> result = dishService.getDishByCid(categoryId);

        // 保存到redis

        redisTemplate.opsForValue().set(key, result);

        return Result.success(result);
    }


}
