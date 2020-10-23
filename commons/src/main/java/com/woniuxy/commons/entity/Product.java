package com.woniuxy.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zh_o
 * @since 2020-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Product对象", description="")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String description;

    private Double price;

    private Integer count;

    private Integer buyNumber;

    @ApiModelProperty(value = "1正常   2禁用")
    private Integer status;

    @Version
    private Integer version;

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String DESCRIPTION = "description";

    public static final String PRICE = "price";

    public static final String COUNT = "count";

    public static final String BUY_NUMBER = "buy_number";

    public static final String STATUS = "status";

}
