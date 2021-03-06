package com.woniuxy.product.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.woniuxy.commons.param.MediaParam;
import com.woniuxy.commons.param.ParameterParam;
import com.woniuxy.commons.param.ProductParam;
import com.woniuxy.commons.param.PublishParam;
import com.woniuxy.commons.result.ResponseResult;
import com.woniuxy.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

/**
 * 商品控制器
 * @author zh_o
 * @since 2020-10-21
 */
@Slf4j
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Jedis jedis = new Jedis("zhno.xyz", 6379);


    /**
     * 新增商品
     * @param productParam 新增商品信息
     * @return 响应结果集
     */
    @PostMapping("/save")
    public Object save(ProductParam productParam) throws Exception {
        productService.saveProduct(productParam);
        return ResponseResult.success("新增成功");
    }

    /**
     * 下架商品
     * @param uId 操作用户 id
     * @param pId 需要下架商品 id
     */
    @DeleteMapping("/{uId}/{pId}")
    public Object removeProduct(@PathVariable("uId") Integer uId, @PathVariable("pId") Integer pId) throws Exception {
        productService.removeProduct(uId, pId);
        return ResponseResult.success("下架成功");
    }

    /**
     * 上架商品
     * @param publishParam 上架商品信息
     * @return 响应结果集
     */
    @PostMapping("/add")
    public Object add(PublishParam publishParam) throws Exception {
        System.out.println(publishParam);
        productService.addProduct(publishParam);
        return ResponseResult.success("上架成功");
    }

    /**
     * 更新商品参数信息
     * @param parameterParam 商品参数信息
     */
    @PutMapping("parameter")
    public Object updateParams(ParameterParam parameterParam) throws Exception {
        log.info("传输参数:  {}", parameterParam);
        productService.updateParam(parameterParam);
        return ResponseResult.success("更新成功");
    }

    /**
     * 根据商品 ID 获取商品参数信息
     * @param pId 商品 ID
     */
    @GetMapping("/parameter/{pId}")
    public Object getParams(@PathVariable("pId") Integer pId) throws Exception {
        // 浏览量 + 1
        jedis.incr("product:" + pId + ":count");
        return ResponseResult.success(productService.getParams(pId));
    }

    /**
     * 商品媒体属性维护
     * @param mediaParam 媒体属性参数
     */
    @PutMapping("/media")
    public Object updateMedia(MediaParam mediaParam) {
        log.info("传输参数:  {}", mediaParam);
        productService.updateMedia(mediaParam);
        return ResponseResult.success("更新成功");
    }

    /**
     * 获取发布商品列表
     * @param start 起始
     * @param end 结束
     */
    @GetMapping("/publishList")
    public Object getPublishList(Integer start, Integer end) throws JsonProcessingException {
        return ResponseResult.success(productService.listPublish(start, end));
    }

    /**
     * 获取商品排行榜
     */
    @GetMapping("/ranking")
    public Object getRanking(Integer start, Integer end) throws JsonProcessingException {
        return ResponseResult.success(productService.getRanking(start, end));
    }

}

