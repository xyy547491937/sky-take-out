package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.QiniuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Api(tags = "公共类接口")
@RestController
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private QiniuUtil qiniuUtil;
    @PostMapping("/upload")
    @ApiOperation("上传照片")
    public Result<String> upload(@RequestBody MultipartFile file) {
        String s = qiniuUtil.uploadFile(file);
        return  Result.success(s);
    }
}
