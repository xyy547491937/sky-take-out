package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "c端购物车相关接口")
public class ShopCartController {
    @Autowired
    private ShopCartService shopCartService;

    /*
    * 添加购物车
    * */
    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车的数据为{}", shoppingCartDTO);
        shopCartService.add(shoppingCartDTO);
        return Result.success("添加成功");
    }

    /*
     * 查看购物车
     * */
    @ApiOperation("查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> getCart() {
        log.info("查看购物车的数据为{}");
        List<ShoppingCart> result =  shopCartService.getCart();
        return Result.success(result);
    }

    /*
     * 删除购物车
     * */
    @ApiOperation("删除购物车")
    @PostMapping("/sub")
    public Result delete(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("查看购物车的数据为{}",shoppingCartDTO);
          shopCartService.delete(shoppingCartDTO);
        return Result.success("删除成功");
    }

    /*
     * 删除购物车
     * */
    @ApiOperation("删除全部购物车")
    @DeleteMapping("/clean")
    public Result deleteAll() {
        log.info("查看购物车的数据为{}");
        shopCartService.deleteAll();
        return Result.success("删除成功");
    }
}
