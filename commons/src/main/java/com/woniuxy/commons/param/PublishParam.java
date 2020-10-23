package com.woniuxy.commons.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh_o
 * @date 2020/10/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishParam {

    private Integer pId;

    private Integer uId;

    private Integer number;

    private String endTime;

    private Double price;

}
