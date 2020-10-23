package com.woniuxy.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zh_o
 * @date 2020/10/22
 */
@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
public class OrderStarter {

    public static void main(String[] args) {
        SpringApplication.run(OrderStarter.class, args);
    }

}
