package com.woniuxy.file.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云 COS 配置类
 * @author zh_o
 * @date 2020-10-24
 */
@Configuration
public class CosConfig {

    @Value("${tencent.cloud.cos.secretId}")
    private String secretId;

    @Value("${tencent.cloud.cos.secretKey}")
    private String secretKey;

    @Value("${tencent.cloud.cos.regionName}")
    private String regionName;

    @Bean
    public COSClient createCosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cred, clientConfig);
    }

}
