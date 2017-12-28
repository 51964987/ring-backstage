package ringbackstage.web.service.role;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.role.AdminRoleResourceMapper;
import ringbackstage.web.model.role.AdminRoleResource;

@Service
public class AdminRoleResourceServiceImpl implements AdminRoleResourceService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AdminRoleResourceMapper adminRoleResourceMapper;
	
	@Transactional
	@Override
	public int accredit(String roleId, List<AdminRoleResource> roleResources) throws ResultException {
		try {
			if(StringUtils.isNotEmpty(roleId)){
				adminRoleResourceMapper.deleteByRoleId(roleId);
			}
			if(roleResources != null && roleResources.size() > 0){
				return adminRoleResourceMapper.add(roleResources);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
		return 0;
	}
	@Override
	public List<AdminRoleResource> findList(AdminRoleResource adminRoleResource) throws ResultException {
		try {
			return adminRoleResourceMapper.findList(adminRoleResource);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}	
}