package com.woniuxy.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="Publish对象", description="")
public class Publish implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pId;

    private Integer uId;

    private Integer number;

    private String endTime;

    private Double price;

    private String addTime;

    @Version
    private Integer version;

    public static final String ID = "id";

    public static final String P_ID = "p_id";

    public static final String U_ID = "u_id";

    public static final String NUMBER = "number";

    public static final String END_TIME = "end_time";

    public static final String PRICE = "price";

    public static final String ADD_TIME = "add_time";

}
