package ${servicePackage};

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ${mapperPackage}.${mapperClassName};
import ${beanPackage}.${beanName};
import ringbackstage.web.model.user.AdminUser;

@Service
public class ${serviceImplClassName} implements ${serviceClassName}{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ${mapperClassName} ${mapperClassName?uncap_first};

	@Override
	public int add(${beanName} ${beanName?uncap_first},AdminUser loginUser) throws ResultException {
		try {
			${beanName?uncap_first}.setDelFlag("0");
			${beanName?uncap_first}.setCreateBy(loginUser.getLoginName());
			${beanName?uncap_first}.setCreateDate(new Date());
			${beanName?uncap_first}.setId(UUID.randomUUID().toString());
			return ${mapperClassName?uncap_first}.add(${beanName?uncap_first});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int delete(${beanName} ${beanName?uncap_first},AdminUser loginUser) throws ResultException {
		try {
			${beanName?uncap_first}.setUpdateBy(loginUser.getLoginName());
			${beanName?uncap_first}.setUpdateDate(new Date());
			return ${mapperClassName?uncap_first}.delete(${beanName?uncap_first});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int update(${beanName} ${beanName?uncap_first},AdminUser loginUser) throws ResultException {
		try {
			${beanName?uncap_first}.setUpdateBy(loginUser.getLoginName());
			${beanName?uncap_first}.setUpdateDate(new Date());
			return ${mapperClassName?uncap_first}.update(${beanName?uncap_first});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public ${beanName} findById(String id)throws ResultException {
		try {
			return ${mapperClassName?uncap_first}.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
	
	@Override
	public List<${beanName}> findList(${beanName} ${beanName?uncap_first})  throws ResultException {
		try {
			return ${mapperClassName?uncap_first}.findList(${beanName?uncap_first});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
	
	@Override
	public List<${beanName}> trees(${beanName} ${beanName?uncap_first})  throws ResultException {
		try {
			return ${mapperClassName?uncap_first}.trees(${beanName?uncap_first});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
}