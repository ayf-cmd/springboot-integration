package com.mybatis.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity implements Serializable {

    private Long id;
    private String orderId;
    private Long orderMoney;

}
