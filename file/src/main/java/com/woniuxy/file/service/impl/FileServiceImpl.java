package com.woniuxy.file.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import com.woniuxy.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件处理业务层实现类
 * @author zh_o
 * @date 2020-10-24
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${tencent.cloud.cos.bucketName}")
    private String bucketName;

    @Autowired
    private COSClient cosClient;

    /**
     * 文件上传
     *
     * @param file 源文件文件
     */
    @Override
    public String upload(MultipartFile file) throws Exception {
        log.info("文件名: {}", file.getOriginalFilename());
        // 创建本地临时文件
        File localFile = File.createTempFile("temp", null);
        // 将上传文件加载到本地临时文件
        file.transferTo(localFile);
        log.info("输出文件: {}", localFile.getPath());
        // 设置唯一文件名
        String fileName = IdUtil.simpleUUID() + "." + FileNameUtil.getSuffix(file.getOriginalFilename());
        // 指定上传到 COS 对象键
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        // 关闭客户端
        cosClient.shutdown();
        // 定义文件访问前缀
        String prefix = "https://img-1301911101.cos.ap-chongqing.myqcloud.com/";
        // 返回文件访问地址
        return prefix + putObjectRequest.getKey();
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @return 文件
     * @throws Exception io
     */
    @Override
    public Object download(String fileName) throws Exception {
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        // 下载对象的 CRC64
        String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        // 关闭输入流
        cosObjectInput.close();
        return crc64Ecma;
    }
}
