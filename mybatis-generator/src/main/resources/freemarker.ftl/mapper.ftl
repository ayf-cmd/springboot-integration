<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${module}.mapper.${entityName}Mapper">
	<sql id="Base_Column_List">
	${agile}
	</sql>
	
	<!-- 详情 -->
	<select id="detail" parameterType="${module}.vo.${entityName}Vo" resultType="${module}.dto.${entityName}Dto">
		select
		<include refid="Base_Column_List" />
		from ${table}
		<where>
			is_delete = 0 
			<#list cis as ci>
			<#if ci.column!="is_delete">
			<if test="${ci.property}  != null ">
				AND ${ci.column} = ${r'#{'}${ci.property}}
			</if>
			</#if>
			</#list>
		</where>
	</select>
	
	<!-- 列表查询 -->
	<select id="listPage" parameterType="${module}.vo.${entityName}QueryVo" resultType="${module}.dto.${entityName}Dto">
		select
		<include refid="Base_Column_List" />
		from ${table}
		<where>
			is_delete = 0 
			<#list cis as ci>
			<#if ci.column=="id">
			<if test="${ci.property}  != null ">
				AND ${ci.column} = ${r'#{'}${ci.property}}
			</if>
			</#if>
			</#list>
		</where>
	</select>
	
	<!-- 弹框查询 -->
	<select id="listCmb" parameterType="${module}.vo.${entityName}QueryVo" resultType="${module}.dto.${entityName}Dto">
		select
		<include refid="Base_Column_List" />
		from ${table}
		<where>
			is_delete = 0 
			<#list cis as ci>
			<#if ci.column=="id">
			<if test="${ci.property} != null ">
				AND ${ci.column} = ${r'#{'}${ci.property}}
			</if>
			</#if>
			</#list>
		</where>
	</select>
	
</mapper>