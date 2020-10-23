package com.woniuxy.order.controller;

import com.woniuxy.commons.param.CartParam;
import com.woniuxy.commons.result.ResponseResult;
import com.woniuxy.order.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车控制器
 * @author zh_o
 * @date 2020/10/22
 */
@RestController
@Api(tags = "购物车接口")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     * @param cartParam 购物车信息
     */
    @PostMapping("/cart/saveCart")
    @ApiOperation("添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pId", value = "商品 ID", paramType = "query"),
            @ApiImplicitParam(name = "uId", value = "用户 ID", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "数量", paramType = "query")
    })
    public Object saveCart(CartParam cartParam) {
        cartService.saveCart(cartParam);
        return ResponseResult.success("添加成功");
    }

}
