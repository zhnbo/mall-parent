package com.woniuxy.order.service;

import com.woniuxy.commons.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.commons.param.OrderParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zh_o
 * @since 2020-10-23
 */
public interface OrderService extends IService<Order> {


    /**
     * 下单
     * @param orderItemParam 订单信息
     */
    void addOrder(OrderParam orderItemParam);
}
