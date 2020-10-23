package com.woniuxy.product;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.woniuxy.product.config.MyBlockHandler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zh_o
 * @date 2020/10/23
 */
@Component
public class ProductApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebCallbackManager.setUrlBlockHandler(new MyBlockHandler());
    }

}
