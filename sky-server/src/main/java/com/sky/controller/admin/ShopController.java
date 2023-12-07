package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "店铺营业接口")
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    /*
     * params status 1 营业中 0 停止营业
     * 设置营业状态
     * */
    @ApiOperation("设置营业状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置营业状态的传入参数为{}", status);

        redisTemplate.opsForValue().set("SHOP_STATUS", status);

        return Result.success("修改成功");
    }

    @ApiOperation("获取营业状态")
    @GetMapping("/status")
    public Result getStatus() {
        log.info("获取营业状态的传入参数为");

        Integer shop_status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");

        return Result.success(shop_status);
    }
}
