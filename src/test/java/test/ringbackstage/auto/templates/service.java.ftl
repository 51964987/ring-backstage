package ${servicePackage};

import java.util.List;

import ringbackstage.common.exception.ResultException;
import ${modelPackage}.${beanName};
import ringbackstage.web.model.user.AdminUser;

public interface ${serviceClassName} {
	<#if isCreateADUQ == true>int add(${beanName} ${beanName?uncap_first},AdminUser loginUser)throws ResultException;
	int delete(${beanName} ${beanName?uncap_first},AdminUser loginUser)throws ResultException;
	int update(${beanName} ${beanName?uncap_first},AdminUser loginUser)throws ResultException;
	${beanName} findById(String id)throws ResultException;
	List<${beanName}> findList(${beanName} ${beanName?uncap_first})throws ResultException;
	</#if><#if isCreateTree == true>List<${beanName}> trees(${beanName} ${beanName?uncap_first})throws ResultException;</#if>
}
