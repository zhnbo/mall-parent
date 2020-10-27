package com.woniuxy.order.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.commons.entity.*;
import com.woniuxy.commons.param.OrderItemParam;
import com.woniuxy.commons.param.OrderParam;
import com.woniuxy.order.mapper.*;
import com.woniuxy.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zh_o
 * @since 2020-10-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private PublishMapper publishMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CartMapper cartMapper;

    private Jedis jedis = new Jedis("zhno.xyz", 6379);

    /**
     * 下单
     *
     * @param orderParam 订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(OrderParam orderParam) {
        // 查询用户信息
        User user = userMapper.selectById(orderParam.getUId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 判断余额
        if (user.getMoney() <= 0) {
            throw new RuntimeException("余额不足");
        }
        // 创建订单实例
        Order order = new Order();
        order.setUId(user.getId());
        // 先新增订单,取得订单 id
        orderMapper.insert(order);
        Integer oId = order.getId();
        // 定义 List 存储订单项
        List<OrderItem> orderItemList = new ArrayList<>();
        // 定义变量，计算订单总价
        Double total = 0.0;
        for (OrderItemParam orderItemParam : orderParam.getProducts()) {
            Publish publish = publishMapper.selectOne(new QueryWrapper<Publish>().in(Publish.P_ID, orderItemParam.getPId()));
            if (publish == null) {
                throw new RuntimeException("商品不存在");
            }
            // 判断库存
            if (publish.getNumber() < orderItemParam.getNumber()) {
                throw new RuntimeException("库存不足");
            }
            // 创建订单项实例
            OrderItem orderItem = BeanUtil.copyProperties(orderItemParam, OrderItem.class);
            // 计算当前订单项总价
            orderItem.setTotal(publish.getPrice() * orderItemParam.getNumber());
            // 设置订单 id
            orderItem.setOId(oId);
            // 存入订单项 List
            orderItemList.add(orderItem);
            // 对应商品减去库存
            int updateRow = publishMapper.update(publish.setNumber(publish.getNumber() - orderItem.getNumber()),
                    new QueryWrapper<Publish>().eq(Publish.ID, publish.getId()).ge(Publish.NUMBER, orderItem.getNumber()));
            if (updateRow < 1) {
                throw new RuntimeException("服务器异常");
            }
            if (user.getMoney() <= orderItem.getTotal()) {
                throw new RuntimeException("余额不足");
            }
            // 减去余额
            user.setMoney(user.getMoney() - orderItem.getTotal());
            // 新增商品购买量
            Transaction multi = jedis.multi();
            jedis.hincrBy("product:" + orderItem.getPId(),"buyNumber", orderItem.getNumber());
            multi.exec();
            // 清楚购物车信息
            Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>().eq(Cart.P_ID, orderItem.getPId()).eq(Cart.U_ID, user.getId()));
            if (cart != null) {
                cartMapper.deleteById(cart);
            }
            // 计算订单总价
            total += orderItem.getTotal();
        }
        // 判断用户余额是否足够
        if (user.getMoney() < total) {
            throw new RuntimeException("余额不足");
        }
        // 设置订单总价
        order.setTotal(total);
        // 新增订单项数据
        orderItemList.forEach(i -> orderItemMapper.insert(i));
        // 修改订单总价
        orderMapper.updateById(order);
        // 修改用户余额
        userMapper.updateById(user);
    }
}
