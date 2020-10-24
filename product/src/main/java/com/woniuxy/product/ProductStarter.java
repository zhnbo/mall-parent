package com.woniuxy.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zh_o
 * @date 2020/10/21
 */
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.woniuxy.product.mapper")
public class ProductStarter {

    public static void main(String[] args) {
        SpringApplication.run(ProductStarter.class, args);
    }

}
