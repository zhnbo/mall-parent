package com.woniuxy.commons.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zh_o
 * @since 2020-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CartParam implements Serializable {

    @NotNull
    private Integer pId;

    @NotNull
    private Integer uId;

    @Min(0)
    @NotNull
    private Integer number;

}
