package com.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * value : Bean的Schemal,实体类首字母小写即可 , 必填
 * description : Bean描述信息 , 必填
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "RequestEntity", description = "订单实体类")
public class ReqEntity implements Serializable {
    /**
     * value : 参数说明 , 必填
     * example : 样例, 当为Bean或者List<Bean>时无需填写 , 可选
     * required : 是否必填 , 可选
     * hidden : 是否隐藏参数,默认为false, 可选
     */
    @ApiModelProperty(value = "姓名", required = true, example = "anyf", hidden = false)
    private String name;
    @ApiModelProperty(value = "年龄", example = "29")
    private Integer age;
    // 实体Bean无需写example,依据UserEntity的swagger进行配置
    @ApiModelProperty(value = "用户列表")
    private List<UserEntity> userEntitys;
    @ApiModelProperty(value = "用户信息")
    private UserEntity userEntity;

}