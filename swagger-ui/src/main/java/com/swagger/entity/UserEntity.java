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
@ApiModel(value = "UserEntity", description = "用户实体类")
public class UserEntity implements Serializable {

    @ApiModelProperty(value = "姓名", required = true, example = "anyf", hidden = false)
    private String name;
    @ApiModelProperty(value = "年龄", example = "29")
    private Integer age;

}
