package com.es.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(indexName = "userrest", shards = 3, replicas = 0, refreshInterval = "1s", createIndex = true)
public class UserEntity implements Serializable {
    @Id
    @Field(type = FieldType.Long, store = true)
    private Long id;
    @Field(name = "age", type = FieldType.Integer, store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private Integer age;
    @Field(type = FieldType.Text, store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String name;
    @Field(type = FieldType.Keyword, store = true)
    private String userId;
    @Field(type = FieldType.Text, store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String hobby;
    @Field(index = false, type = FieldType.Keyword, store = true)
    private String images;
    @Field(type = FieldType.Text, store = false)
    private String notStore;
}
