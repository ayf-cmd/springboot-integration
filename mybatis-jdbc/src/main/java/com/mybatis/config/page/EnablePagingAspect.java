package com.mybatis.config.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EnablePagingAspect {

	@Pointcut("@annotation(com.mybatis.config.page.EnablePaging)")
	private void aspect() {

	}

	@Around(value = "aspect()")
	public <T, E> PageVO<T> doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object[] args = proceedingJoinPoint.getArgs();
		PageParam<T> pageParam = (PageParam<T>) args[0];
		Class<T> tClass = (Class<T>) pageParam.getCondition().getClass();
		String[] orderBy = pageParam.getOrderBy();
		Page<E> page = PageHelper.startPage(pageParam.getCurrent(), pageParam.getPageSize());
		if (orderBy != null && orderBy.length > 0) {
			for (String s : orderBy) {
				page.setOrderBy(s);
			}
		}
		proceedingJoinPoint.proceed(args);
		return PageVO.build(page, tClass);
	}

}
