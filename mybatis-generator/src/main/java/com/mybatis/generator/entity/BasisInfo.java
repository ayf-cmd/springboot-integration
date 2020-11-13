package com.mybatis.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasisInfo implements Serializable {
    private static final long serialVersionUID = 123123L;
    private String project;
    private String author;
    private String version;
    private String dbUrl;
    private String dbName;
    private String dbPassword;
    private String database;
    private String table;
    private String entityName;
    private String objectName;
    private String entityComment;
    private String createTime;
    private String agile;
    private String idType;
    private String idJdbcType;
    private List<PropertyInfo> cis;
    private String sign;
    private String swaggerTags;
    private String subPackage;
    private String module;

    public BasisInfo(String project, String author, String version, String dbUrl, String dbName, String dbPassword,
        String database, String createTime, String agile, String module) {
        super();
        this.project = project;
        this.author = author;
        this.version = version;
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.dbPassword = dbPassword;
        this.database = database;
        this.createTime = createTime;
        this.agile = agile;
        this.module = module;
    }

}
