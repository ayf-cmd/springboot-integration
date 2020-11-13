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
	private String className;
	private String table;
	private String classComment;
	private String module;
}
