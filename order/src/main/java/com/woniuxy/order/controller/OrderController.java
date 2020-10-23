package com.woniuxy.order.controller;

import com.woniuxy.commons.entity.OrderParam;
import com.woniuxy.commons.result.ResponseResult;
import com.woniuxy.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 * @author zh_o
 * @date 2020-10-23
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 下单
     * @param orderParam 订单参数信息
     */
    @PostMapping("/add")
    public Object addOrder(@RequestBody OrderParam orderParam) {
        System.out.println("参数：   " + orderParam);
        orderService.addOrder(orderParam);
        return ResponseResult.success();
    }

}
