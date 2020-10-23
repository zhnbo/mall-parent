package com.woniuxy.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zh_o
 * @date 2020/10/21
 */
@SpringBootApplication
@MapperScan("com.woniuxy.product.mapper")
@EnableSwagger2
public class ProductStarter {

    public static void main(String[] args) {
        SpringApplication.run(ProductStarter.class, args);
    }

}
