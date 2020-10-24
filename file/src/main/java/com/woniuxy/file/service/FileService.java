package com.woniuxy.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理业务层接口
 * @author zh_o
 * @date 2020-10-24
 */
public interface FileService {


    /**
     * 文件上传
     * @param file 源文件
     * @return 文件名
     * @throws Exception io
     */
    String upload(MultipartFile file) throws Exception;

    /**
     * 下载文件
     * @param fileName 文件名
     * @return 文件
     * @throws Exception io
     */
    Object download(String fileName) throws Exception;

}
