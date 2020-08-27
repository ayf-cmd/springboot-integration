package com.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "ResponseEntity", description = "响应参数")
public class ResponseEntity implements Serializable {

    @ApiModelProperty(value = "响应码", required = true, example = "200")
    private Integer code;
    @ApiModelProperty(value = "响应描述", example = "SUCCESS")
    private String message;
    @ApiModelProperty(value = "响应数据")
    private Object data;

}