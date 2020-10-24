package com.woniuxy.commons.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private Integer uId;

    @NotEmpty
    @NotNull
    private List<OrderItemParam> products;

}
