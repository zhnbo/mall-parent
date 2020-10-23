package com.woniuxy.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zh_o
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uId;

    @ApiModelProperty(value = "订单总价")
    private Double total;

    private Integer version;

    @ApiModelProperty(value = "购买时间")
    private Date time;


    public static final String ID = "id";

    public static final String U_ID = "u_id";

    public static final String TOTAL = "total";

    public static final String I_ID = "i_id";

    public static final String VERSION = "version";

    public static final String TIME = "time";

}
