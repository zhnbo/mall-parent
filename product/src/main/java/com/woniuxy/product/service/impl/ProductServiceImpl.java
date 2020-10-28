package com.woniuxy.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniuxy.commons.dto.ParameterDTO;
import com.woniuxy.commons.entity.Operation;
import com.woniuxy.commons.entity.Parameter;
import com.woniuxy.commons.entity.Product;
import com.woniuxy.commons.entity.Publish;
import com.woniuxy.commons.param.MediaParam;
import com.woniuxy.commons.param.ParameterParam;
import com.woniuxy.commons.param.ProductParam;
import com.woniuxy.commons.param.PublishParam;
import com.woniuxy.product.mapper.OperationMapper;
import com.woniuxy.product.mapper.ParameterMapper;
import com.woniuxy.product.mapper.ProductMapper;
import com.woniuxy.product.mapper.PublishMapper;
import com.woniuxy.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zh_o
 * @since 2020-10-21
 */
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OperationMapper operationMapper;

    @Resource
    private PublishMapper publishMapper;

    @Resource
    private ParameterMapper parameterMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StringRedisTemplate srt;

    @Autowired
    private RedisTemplate<Object, Object> rt;

    @Autowired
    private RedisTemplate<String, Object> jrt;

    /**
     * 新增商品
     *
     * @param productParam 新增商品信息
     */
    @Override
    public void saveProduct(ProductParam productParam) {
        // 判断参数是否合法
        if (productParam == null) {
            throw new RuntimeException("参数异常");
        }
        // 复制参数对象
        Product product = BeanUtil.copyProperties(productParam, Product.class);
        // 新增商品数据
        int insertRow = productMapper.insert(product);
        // 判断是否新增成功
        if (insertRow < 1) {
            throw new RuntimeException("新增失败");
        }
        // 新增操作数据
        int row = operationMapper.insert(new Operation().setPId(product.getId()).setUId(productParam.getUId()));
        // 判断是否新增成功
        if (row < 1) {
            throw new RuntimeException("新增失败");
        }
    }

    /**
     * 上架商品
     */
    @Override
    public void addProduct(PublishParam publishParam) throws Exception {
        // 查询商品信息
        Product product = productMapper.selectById(publishParam.getPId());
        if (product == null) {
            throw new RuntimeException("该商品不存在");
        }
        // 判断商品是否可用
        if (product.getStatus().equals(0)) {
            throw new Exception("商品已禁用");
        }
        // 判断库存
        if (product.getCount() < publishParam.getNumber()) {
            throw new Exception("商品库存不足");
        }
        // 是否已上架
        Integer i = 2;
        if (product.getStatus().equals(i)) {
            // 查询发布数据
            Publish publish = publishMapper.selectOne(new QueryWrapper<Publish>().eq(Publish.P_ID, publishParam.getPId()));
            // 已上架,修改对应数据
            BeanUtil.copyProperties(publishParam, publish);
            System.out.println("发布数据:   " + publish);
            // 修改发布表数据
            publishMapper.updateById(publish);
            // 修改对应 Redis 数据
            jrt.opsForHash().put("publish", publish.getId() + "", publish);
            // 减少库存,修改商品状态
            product.setCount(product.getCount() - publishParam.getNumber());
            product.setStatus(2);
            productMapper.updateById(product);
            return;
        }
        // 未上架,新增发布表数据
        Publish publish = BeanUtil.copyProperties(publishParam, Publish.class);
        System.out.println("发布数据:   " + publish);
        publishMapper.insert(publish);
        // 新增到 Redis
        publish.setAddTime(new Date().toString());
        jrt.opsForHash().put("publish", publish.getId() + "", publish);
        // 将 id 存入商品列表
        jrt.opsForList().leftPush("publish:list", publish.getId());
        // 减少库存,修改商品状态
        product.setCount(product.getCount() - publishParam.getNumber());
        product.setStatus(2);
        productMapper.updateById(product);
    }

    /**
     * 下架商品
     *
     * @param uId 用户 id
     * @param pId 商品 id
     */
    @Override
    public void removeProduct(Integer uId, Integer pId) {
        // 判断参数是否合法
        if (uId == null || pId == null) {
            throw new RuntimeException("参数异常");
        }
        // 判断商品是否已上架
        Product product = productMapper.selectById(pId);
        // 判断商品是否存在
        if (product == null) {
            throw new RuntimeException("该商品不存在");
        }
        if (product.getStatus() != 2) {
            // 未上架
            throw new RuntimeException("商品未上架");
        }
        // 已上架
        Publish publish = publishMapper.selectOne(new QueryWrapper<Publish>().eq(Publish.P_ID, pId));
        // 还原库存,修改状态
        product.setStatus(1);
        product.setCount(product.getCount() + publish.getNumber());
        productMapper.updateById(product);
        // 删除发布数据
        publishMapper.deleteById(publish);
    }

    /**
     * 新增商品参数信息
     *
     * @param parameterParam 商品参数信息
     */
    @Override
    public void updateParam(ParameterParam parameterParam) throws Exception {
        // 判断商品是否存在
        productIsExists(parameterParam.getPId());
        // 判断数据是否存在
        Parameter parameter = parameterMapper.selectOne(new QueryWrapper<Parameter>().eq(Parameter.P_ID, parameterParam.getPId()));
        if (parameter != null) {
            BeanUtil.copyProperties(parameterParam, parameter);
            log.info("修改数据:  {}", parameter);
            parameterMapper.updateById(parameter);
            return;
        }
        Parameter params = BeanUtil.copyProperties(parameterParam, Parameter.class);
        log.info("新增数据:  {}", params);
        parameterMapper.insert(params);
    }

    /**
     * 获取商品参数信息
     *
     * @param pId 商品 ID
     */
    @Override
    public ParameterDTO getParams(Integer pId) throws Exception {
        // 判断参数是否合法
        if (pId == null) {
            throw new RuntimeException("参数异常");
        }
        Parameter parameter = parameterMapper.selectOne(new QueryWrapper<Parameter>().eq(Parameter.P_ID, pId));
        if (parameter == null) {
            throw new RuntimeException("未找到该商品信息");
        }
        ParameterDTO parameterDTO = BeanUtil.copyProperties(parameter, ParameterDTO.class);
        parameterDTO.setParam(objectMapper.readValue(parameter.getParams(), List.class));
        return parameterDTO;
    }

    /**
     * 商品媒体属性维护
     *
     * @param mediaParam 商品媒体属性信息
     */
    @Override
    public void updateMedia(MediaParam mediaParam) {
        // 判断商品是否存在
        productIsExists(mediaParam.getPId());
        // 判断是否存在媒体属性
        Parameter parameter = parameterMapper.selectOne(new QueryWrapper<Parameter>().eq(Parameter.P_ID, mediaParam.getPId()));
        if (parameter != null) {
            BeanUtil.copyProperties(mediaParam, parameter);
            log.info("修改数据:  {}", parameter);
            parameterMapper.updateById(parameter);
            return;
        }
        Parameter params = BeanUtil.copyProperties(mediaParam, Parameter.class);
        log.info("新增数据:  {}", params);
        parameterMapper.insert(params);
    }


    /**
     * 判断是否存在
     */
    private Product productIsExists (Integer pId) {
        // 判断商品是否存在
        Product product = productMapper.selectById(pId);
        if (pId == null) {
            throw new RuntimeException("商品不存在");
        }
        return product;
    }

    /**
     * 获取商品列表
     *
     * @param start 起始
     * @param end   结束
     */
    @Override
    public List<Publish> listPublish(Integer start, Integer end) throws JsonProcessingException {
        // 查询商品列表
        List<Object> list = jrt.opsForList().range("publish:list", start, end);
        // 根据 ID 获取商品详细信息
        ArrayList<Publish> publishList = new ArrayList<>();
        if (list == null) {
            throw new RuntimeException("无数据");
        }
        for (Object o : list) {
            Integer i = Integer.valueOf(o + "");
            Publish publish = objectMapper.readValue(objectMapper.writeValueAsString(jrt.opsForHash().get("publish", i + "")), Publish.class);
            publishList.add(publish);
        }
        return publishList;
    }

    @Override
    public List<Publish> getRanking(Integer start, Integer end) throws JsonProcessingException {
        // 查询商品列表
        List<Object> list = jrt.opsForList().range("publish:list", start, end);
        // 根据 ID 获取商品详细信息
        ArrayList<Publish> publishList = new ArrayList<>();
        if (list == null) {
            throw new RuntimeException("无数据");
        }
        for (Object o : list) {
            Integer i = Integer.valueOf(o + "");
            Publish publish = objectMapper.readValue(objectMapper.writeValueAsString(jrt.opsForHash().get("publish", i + "")), Publish.class);
            publishList.add(publish);
        }
        // 存入到排行榜列表

        return publishList;
    }
}
