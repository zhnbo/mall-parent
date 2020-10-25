package com.woniuxy.commons.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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

    @NotNull
    private Integer uId;

    @NotNull
    private Integer pId;

    @NotNull
    private List<String> media;
}
