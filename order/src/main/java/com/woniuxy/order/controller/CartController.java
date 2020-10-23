package com.woniuxy.order.controller;

import com.woniuxy.commons.param.CartParam;
import com.woniuxy.commons.result.ResponseResult;
import com.woniuxy.order.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车控制器
 * @author zh_o
 * @date 2020/10/22
 */
@RestController
@Api(tags = "购物车接口")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     * @param cartParam 购物车信息
     */
    @PostMapping
    public Object saveCart(@Validated CartParam cartParam) {
        cartService.saveCart(cartParam);
        return ResponseResult.success("添加成功");
    }

    /**
     * 修改购物车
     * @param cartParam 购物车信息
     */
    @PutMapping
    public Object updateCart(@Validated CartParam cartParam) {
        cartService.updateCart(cartParam);
        return ResponseResult.success("修改成功");
    }


}
