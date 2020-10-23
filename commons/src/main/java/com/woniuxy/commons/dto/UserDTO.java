package com.woniuxy.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;

    private String tel;

    private String name;

    private Integer age;


}
