package com.woniuxy.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.commons.entity.Cart;
import com.woniuxy.commons.param.CartParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zh_o
 * @since 2020-10-22
 */
public interface CartService extends IService<Cart> {

    /**
     * 添加购物车
     * @param cartParam 购物车信息
     */
    void saveCart(CartParam cartParam);

    /**
     * 修改购物车
     * @param cartParam 购物车信息
     */
    void updateCart(CartParam cartParam);

}
