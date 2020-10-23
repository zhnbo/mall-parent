package com.woniuxy.commons.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zh_o
 * @date 2020/10/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductParam implements Serializable {

    private Integer uId;

    private String title;

    private String description;

    private Double price;

    private Integer count;

}
