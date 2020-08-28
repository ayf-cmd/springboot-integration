package com.mybatis.config.page;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageParam<T> implements Serializable{
	/**
	 * 按创建时间倒序排序
	 */
	public static final String ORDER_BY_CREATE_TIME_DESC = "id desc";

	@Builder.Default
	@Range(min = 1, max = Integer.MAX_VALUE)
	private int current = 1;
	
	@Builder.Default
	@Range(min = 1, max = Integer.MAX_VALUE)
	private int pageSize = 30;
	
	private T condition;
	
	@Builder.Default
	private String[] orderBy = new String[] {" id desc "};
	
	public PageParam(int pageNum, int pageSize) {
		super();
		this.current = pageNum;
		this.pageSize = pageSize;
	}
	
	public int getOffset() {
		return (this.current - 1) * this.pageSize;
	}
	
}
