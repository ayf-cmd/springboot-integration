package com.mybatis.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 类名称
	 */
	private String className;
	/**
	 * 数据库表名
	 */
	private String table;
	/**
	 * 类说明
	 */
	private String classComment;
	/**
	 * 模块 : PROJECT.PACKAGE.module 为完整类路径
	 */
	private String module;
}
