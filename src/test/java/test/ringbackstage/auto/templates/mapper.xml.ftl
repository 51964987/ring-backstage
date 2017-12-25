<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapperPackage}.${mapperClassName}">
	
	<!-- 增加 -->
	<insert id="add" parameterType="${beanPackage}.${beanName}">
		INSERT INTO ${tableName} (<#list cols as col><#if col_index &gt; 0>,</#if>${col.COLUMN_NAME}</#list>)
		VALUES(<#list cols as col><#if col_index &gt; 0>,</#if>${r'#{'}${col.FIELD_NAME}${r'}'}</#list>)
	</insert>

	<!-- 删除 -->
	<update id="delete" parameterType="${beanPackage}.${beanName}">
		UPDATE ${tableName} SET
		del_flag = '1',
		update_date = ${r'#{updateDate}'},
		update_by = ${r'#{updateBy}'}
		WHERE id = ${r'#{id}'}
	</update>
	
	<!-- 修改 -->
	<update id="update" parameterType="${beanPackage}.${beanName}">
		UPDATE ${tableName} SET
		update_date = ${r'#{updateDate}'},
		update_by = ${r'#{updateBy}'},
		remarks = ${r'#{remarks}'}
		WHERE id = ${r'#{id}'}
	</update>
	
	<!-- 未删除的列表 -->
	<select id="findList" resultType="${beanPackage}.${beanName}" resultMap="${beanName?uncap_first}ResultMap">
		SELECT t.*,t.id as oper FROM ${tableName} t
		WHERE t.del_flag &lt;&gt; 1
		<if test=" name != null and name != '' ">
			AND t.name like '%${r'${name}'}%'
		</if>
		ORDER BY t.create_date DESC
	</select>
	
	<!-- 根据ID查询 -->
	<select id="findById" resultType="${beanPackage}.${beanName}" resultMap="${beanName?uncap_first}ResultMap">
		SELECT t.*,t.id as oper FROM ${tableName} t
		WHERE t.id = ${r'#{id}'}
	</select>
	
	<!-- 映射 -->
	<resultMap type="${beanPackage}.${beanName}" id="${beanName?uncap_first}ResultMap">
		<#list cols as col>
		<result property="${col.FIELD_NAME}" column="${col.COLUMN_NAME}" />
		</#list>
	</resultMap>
</mapper>