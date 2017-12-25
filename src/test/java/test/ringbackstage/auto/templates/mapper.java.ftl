package ${mapperPackage};

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ${beanPackage}.${beanName};

@Mapper
public interface ${mapperClassName} {
	int add(${beanName} ${beanName?uncap_first})throws Exception;
	int delete(${beanName} ${beanName?uncap_first})throws Exception;
	int update(${beanName} ${beanName?uncap_first})throws Exception;
	${beanName} findById(String id)throws Exception;
	List<${beanName}> findList(${beanName} ${beanName?uncap_first})throws Exception;
	List<${beanName}> trees(${beanName} ${beanName?uncap_first})throws Exception;
}
