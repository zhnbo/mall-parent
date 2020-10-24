package com.woniuxy.commons.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 媒体属性实体
 * @author zh_o
 * @date 2020/10/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MediaParam {

    private Integer uId;

    private Integer pId;

    private List<String> media;
}
