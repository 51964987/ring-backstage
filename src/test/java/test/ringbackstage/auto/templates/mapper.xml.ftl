<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${daoPackage}.${daoClassName}">
	<#if isCreateADUQ == true>
	<!-- 增加 -->
	<insert id="add" parameterType="${modelPackage}.${beanName}">
		INSERT INTO ${tableName} (<#list cols as col><#if col_index &gt; 0>,</#if>${col.COLUMN_NAME}</#list>)
		VALUES(<#list cols as col><#if col_index &gt; 0>,</#if>${r'#{'}${col.FIELD_NAME}${r'}'}</#list>)
	</insert>

	<!-- 删除 -->
	<update id="delete" parameterType="${modelPackage}.${beanName}">
		UPDATE ${tableName} SET
		del_flag = '1',
		update_date = ${r'#{updateDate}'},
		update_by = ${r'#{updateBy}'}
		WHERE id = ${r'#{id}'}
	</update>
	
	<!-- 修改 -->
	<update id="update" parameterType="${modelPackage}.${beanName}">
		UPDATE ${tableName} SET
		update_date = ${r'#{updateDate}'},
		update_by = ${r'#{updateBy}'},
		remarks = ${r'#{remarks}'}
		WHERE id = ${r'#{id}'}
	</update>
	
	<!-- 未删除的列表 -->
	<select id="findList" resultType="${modelPackage}.${beanName}" resultMap="${beanName?uncap_first}ResultMap">
		SELECT t.*,t.id as oper FROM ${tableName} t
		WHERE t.del_flag &lt;&gt; 1
		<if test=" name != null and name != '' ">
			AND t.name like '%${r'${name}'}%'
		</if>
		<if test=" parentId != null and parentId != '' ">
			AND (FIND_IN_SET(${r'#{parentId}'},t.parent_ids) > 0 OR t.id = ${r'#{parentId}'} )
		</if>
		ORDER BY t.create_date DESC
	</select>
	
	<!-- 根据ID查询 -->
	<select id="findById" resultType="${modelPackage}.${beanName}" resultMap="${beanName?uncap_first}ResultMap">
		SELECT t.*,t.id as oper FROM ${tableName} t
		WHERE t.id = ${r'#{id}'}
	</select>
	</#if>
	<#if isCreateTree == true>
	<!-- 左边树形 -->
	<select id="trees" resultType="${modelPackage}.${beanName}" resultMap="${beanName?uncap_first}ResultMap">
		SELECT * FROM ${tableName}
		WHERE del_flag &lt;&gt; 1
		<if test=" parentId != null and parentId != '' ">
			AND parent_id = ${r'#{parentId}'}
		</if>
		<if test=" parentId == null or parentId == '' ">
			AND (parent_id IS NULL OR parent_id = '')
		</if>		
		ORDER BY sort,create_date DESC
	</select>
	</#if>
	<!-- 映射 -->
	<resultMap type="${modelPackage}.${beanName}" id="${beanName?uncap_first}ResultMap">
		<#list cols as col>
		<result property="${col.FIELD_NAME}" column="${col.COLUMN_NAME}" />
		</#list>
	</resultMap>
</mapper>