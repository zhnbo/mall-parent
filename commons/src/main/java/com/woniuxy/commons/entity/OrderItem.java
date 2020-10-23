package com.woniuxy.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pId;

    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 订单 id
     */
    private Integer oId;

    /**
     * 总价
     */
    private Double total;

    private Integer version;


    public static final String ID = "id";

    public static final String P_ID = "p_id";

    public static final String NUMBER = "number";

    public static final String O_ID = "o_id";

    public static final String TOTAL = "total";

    public static final String VERSION = "version";

}
