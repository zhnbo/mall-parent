package com.woniuxy.product.controller;


import com.woniuxy.commons.param.ParameterParam;
import com.woniuxy.commons.param.ProductParam;
import com.woniuxy.commons.param.PublishParam;
import com.woniuxy.commons.result.ResponseResult;
import com.woniuxy.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制器
 * @author zh_o
 * @since 2020-10-21
 */
@RestController
@Api(tags = "商品接口")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 新增商品
     * @param productParam 新增商品信息
     * @return 响应结果集
     */
    @PostMapping("/product/save")
    @ApiOperation("新增商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uId", value = "操作用户 ID", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "商品标题"),
            @ApiImplicitParam(name = "description", value = "商品描述"),
            @ApiImplicitParam(name = "price", value = "商品价格"),
            @ApiImplicitParam(name = "count", value = "商品库存"),
    })
    public Object save(ProductParam productParam) throws Exception {
        productService.saveProduct(productParam);
        return ResponseResult.success("新增成功");
    }

    /**
     * 下架商品
     * @param uId 操作用户 id
     * @param pId 需要下架商品 id
     */
    @DeleteMapping("/product")
    @ApiOperation("下架商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uId", value = "用户 ID"),
            @ApiImplicitParam(name = "pId", value = "商品 ID")
    })
    public Object removeProduct(Integer uId, Integer pId) throws Exception {
        productService.removeProduct(uId, pId);
        return ResponseResult.success("下架成功");
    }

    /**
     * 上架商品
     * @param publishParam 上架商品信息
     * @return 响应结果集
     */
    @PostMapping("/product/add")
    @ApiOperation("上架商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pId", value = "商品 ID", paramType = "query"),
            @ApiImplicitParam(name = "uId", value = "用户 ID", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "上架数量"),
            @ApiImplicitParam(name = "endTime", value = "商品结束售卖时间"),
            @ApiImplicitParam(name = "price", value = "商品售价"),
    })
    public Object add(PublishParam publishParam) throws Exception {
        System.out.println(publishParam);
        productService.addProduct(publishParam);
        return ResponseResult.success("上架成功");
    }

    /**
     * 更新商品参数信息
     * @param parameterParam 商品参数信息
     */
    @ApiOperation("更新商品参数信息")
    @PostMapping("/product/addParam")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pId", value = "商品 ID", paramType = "query"),
            @ApiImplicitParam(name = "uId", value = "用户 ID", paramType = "query"),
            @ApiImplicitParam(name = "params", value = "商品参数信息", paramType = "query"),
    })
    public Object updateParams(ParameterParam parameterParam) throws Exception {
        log.info("传输参数:  {}", parameterParam);
        productService.updateParam(parameterParam);
        return ResponseResult.success("更新成功");
    }

    /**
     * 根据商品 ID 获取商品参数信息
     * @param pId 商品 ID
     */
    @ApiOperation("获取商品参数信息")
    @GetMapping("/product/params")
    @ApiImplicitParam(name = "pId", value="商品 ID")
    public Object getParams(Integer pId) throws Exception {
        return ResponseResult.success(productService.getParams(pId));
    }

}

