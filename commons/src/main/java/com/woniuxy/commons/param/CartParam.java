package com.woniuxy.commons.param;

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
 * @since 2020-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CartParam implements Serializable {

    private Integer pId;

    private Integer uId;

    private Integer number;

}
