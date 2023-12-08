package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "管理端套餐相关接口")
@RestController
@RequestMapping("admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("套餐分类分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
     * 新增套餐
     *
     * */

    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO) {

        setmealService.save(setmealDTO);
        return Result.success("新增成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐详情")
    public Result<SetmealDTO> getSetmealById(@PathVariable Long id) {
        log.info("根据套餐id 获取套餐详情：{}", id);
        SetmealDTO result = setmealService.getSetmealById(id);
        return Result.success(result);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody  SetmealDTO setmealDTO) {
        log.info("修改套餐上送的详情：{}", setmealDTO);
         setmealService.update(setmealDTO);
        return Result.success("修改成功");
    }

}
