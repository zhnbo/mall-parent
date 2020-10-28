package com.woniuxy.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.woniuxy.commons.dto.ParameterDTO;
import com.woniuxy.commons.entity.Product;
import com.woniuxy.commons.entity.Publish;
import com.woniuxy.commons.param.MediaParam;
import com.woniuxy.commons.param.ParameterParam;
import com.woniuxy.commons.param.ProductParam;
import com.woniuxy.commons.param.PublishParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zh_o
 * @since 2020-10-21
 */
public interface ProductService extends IService<Product> {

    /**
     * 新增商品
     * @param productParam 新增商品信息
     */
    void saveProduct(ProductParam productParam) throws Exception;

    /**
     * 上架商品
     * @param publishParam 上架数据
     */
    void addProduct(PublishParam publishParam) throws Exception;

    /**
     * 下架商品
     * @param uId 用户 id
     * @param pId 商品 id
     */
    void removeProduct(Integer uId, Integer pId) throws Exception;

    /**
     * 更新商品参数信息
     * @param parameterParam 商品参数信息
     */
    void updateParam(ParameterParam parameterParam) throws Exception;

    /**
     * 获取商品参数信息
     * @param pId 商品 ID
     */
    ParameterDTO getParams(Integer pId) throws Exception;

    /**
     * 商品媒体属性维护
     * @param mediaParam 商品媒体属性信息
     */
    void updateMedia(MediaParam mediaParam);

    List<Publish> getRanking(Integer start, Integer end) throws JsonProcessingException;

    /**
     * 获取商品列表
     * @param start 起始
     * @param end 结束
     */
    List<Publish> listPublish(Integer start, Integer end) throws JsonProcessingException;
}
