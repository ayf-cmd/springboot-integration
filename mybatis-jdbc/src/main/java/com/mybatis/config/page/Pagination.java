package com.mybatis.config.page;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class Pagination implements Serializable{
	
	private int current;

	private int pageSize;
	
	private long total;

	private int pages;
	
}
