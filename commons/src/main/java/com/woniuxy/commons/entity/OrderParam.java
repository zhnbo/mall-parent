package com.woniuxy.commons.entity;

import com.woniuxy.commons.param.OrderItemParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author zh_o
 * @date 2020-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderParam implements Serializable {

    private Integer uId;

    private List<OrderItemParam> products;

}
