package com.woniuxy.file.controller;

import com.woniuxy.commons.result.ResponseResult;
import com.woniuxy.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 * @author zh_o
 * @date 2020-10-24
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public Object fileUpload(MultipartFile file) throws Exception {
        return ResponseResult.success(fileService.upload(file));
    }

    //@GetMapping("/download")
    //public Object fileDownload(String fileName) throws Exception {
    //    return ResponseResult.success(fileService.download(fileName));
    //}
}
