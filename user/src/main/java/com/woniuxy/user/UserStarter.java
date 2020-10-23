package com.woniuxy.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zh_o
 * @date 2020/10/21
 */
@SpringBootApplication
@EnableSwagger2
public class UserStarter {

    public static void main(String[] args) {
        SpringApplication.run(UserStarter.class, args);
    }

}
