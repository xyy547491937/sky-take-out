package com.sky.controller.user;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController("UserCategoryController")
@RequestMapping("/user/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类分页查询
     * @param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("c端查询分类接口")
    public Result<List<Category>> getALlPage(@RequestParam(value = "type",defaultValue = "") Integer type){
        log.info("分页查询：{}", type);
        List<Category> result = categoryService.getALlPage(type);
        return Result.success(result);
    }

}
