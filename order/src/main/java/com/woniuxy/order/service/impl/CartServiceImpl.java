package com.woniuxy.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.commons.entity.Cart;
import com.woniuxy.commons.entity.Publish;
import com.woniuxy.commons.param.CartParam;
import com.woniuxy.order.mapper.CartMapper;
import com.woniuxy.order.mapper.PublishMapper;
import com.woniuxy.order.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zh_o
 * @since 2020-10-22
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private PublishMapper publishMapper;

    /**
     * 添加购物车
     *
     * @param cartParam 购物车信息
     */
    @Override
    public void saveCart(CartParam cartParam) {
        if (cartParam == null || cartParam.getPId() == null || cartParam.getUId() == null) {
            throw new RuntimeException("参数异常");
        }
        if (cartParam.getNumber() <= 0) {
            throw new RuntimeException("数量不合法");
        }
        Publish publish = publishMapper.selectOne(new QueryWrapper<Publish>().eq(Publish.P_ID, cartParam.getPId()));
        if (publish == null) {
            throw new RuntimeException("商品不存在");
        }
        // 判端是否已存在
        Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>().eq(Cart.P_ID, cartParam.getPId()).eq(Cart.U_ID, cartParam.getUId()));
        if (cart != null) {
            // 已存在改变数量
            cart.setNumber(cartParam.getNumber() + cart.getNumber());
            cartMapper.updateById(cart);
            return;
        }
        cartMapper.insert(BeanUtil.copyProperties(cartParam, Cart.class));
    }
}
