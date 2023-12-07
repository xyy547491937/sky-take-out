package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("userShopController")
@Api(tags = "店铺营业接口")
@RequestMapping("/user/shop")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("获取营业状态")
    @GetMapping ("/status")
    public Result getStatus() {
        log.info("获取营业状态的传入参数为");

        Integer shop_status =(Integer) redisTemplate.opsForValue().get("SHOP_STATUS");

        return Result.success(shop_status);

    }
}
