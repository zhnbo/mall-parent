package com.woniuxy.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zh_o
 * @date 2020-10-25
 */
@FeignClient("file-server")
public interface FileClient {

    @PostMapping("/file/upload")
    public Object fileUpload(MultipartFile file);

}
