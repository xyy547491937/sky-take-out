package com.sky.utils;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class QiniuUtil {

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;

    @Value("${qiniu.bucket-name}")
    private String bucketName;

    @Value("${qiniu.domain}")
    private String domain;

    public String uploadFile(MultipartFile file) {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucketName);

        try {
            byte[] bytes = file.getBytes();

            // 获取文件类型
            String contentType = file.getContentType();
            log.info("文件类型为{}",contentType);

            // 获取文件名
            String originalFilename = file.getOriginalFilename();

            // 使用StringUtils工具类获取文件扩展名
            String fileExtension = StringUtils.getFilenameExtension(originalFilename);
            log.info("文件扩展名{}",fileExtension);
            // 使用 UUID 生成唯一的文件名
            String fileName = UUID.randomUUID().toString();

            // 使用七牛云配置类 Configuration 进行初始化
            Configuration cfg = new Configuration();
            UploadManager uploadManager = new UploadManager(cfg);

            Response response = uploadManager.put(bytes, fileName+'.'+fileExtension, upToken);
            log.info("获取的相应数据为{}",response);
            // 解析上传成功的结果
            DefaultPutRet putRet = response.jsonToObject(DefaultPutRet.class);
            log.info("上传图片获取的解析数据为{}",putRet);
            log.info("上传图片获取的解析数据为{}",putRet.key);
            String imageUrl = domain + "/" + putRet.key;
            return imageUrl;
        } catch (IOException ex) {
            // 处理异常
            ex.printStackTrace();
            return null;
        }
    }
}
