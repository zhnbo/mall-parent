package com.woniuxy.commons.param;

import lombok.Data;

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
public class UserParam implements Serializable {

    private String tel;

    private String name;

    private Integer age;

}
