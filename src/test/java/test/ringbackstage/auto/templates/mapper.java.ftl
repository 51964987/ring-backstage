package ${daoPackage};

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ${modelPackage}.${beanName};

@Mapper
public interface ${daoClassName} {
	<#if isCreateADUQ == true>int add(${beanName} ${beanName?uncap_first})throws Exception;
	int delete(${beanName} ${beanName?uncap_first})throws Exception;
	int update(${beanName} ${beanName?uncap_first})throws Exception;
	${beanName} findById(String id)throws Exception;
	List<${beanName}> findList(${beanName} ${beanName?uncap_first})throws Exception;
	</#if><#if isCreateTree == true>List<${beanName}> trees(${beanName} ${beanName?uncap_first})throws Exception;</#if>
}
