package ringbackstage.web.service.resource;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.resource.AdminResourceMapper;
import ringbackstage.web.model.resource.AdminResource;
import ringbackstage.web.model.user.AdminUser;

@Service
public class AdminResourceServiceImpl implements AdminResourceService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AdminResourceMapper adminResourceMapper;

	@Override
	public int add(AdminResource adminResource,AdminUser loginUser) throws ResultException {
		try {
			adminResource.setDelFlag("0");
			adminResource.setCreateBy(loginUser.getLoginName());
			adminResource.setCreateDate(new Date());
			adminResource.setId(UUID.randomUUID().toString());
			return adminResourceMapper.add(adminResource);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int delete(AdminResource adminResource,AdminUser loginUser) throws ResultException {
		try {
			adminResource.setUpdateBy(loginUser.getLoginName());
			adminResource.setUpdateDate(new Date());
			return adminResourceMapper.delete(adminResource);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int update(AdminResource adminResource,AdminUser loginUser) throws ResultException {
		try {
			adminResource.setUpdateBy(loginUser.getLoginName());
			adminResource.setUpdateDate(new Date());
			return adminResourceMapper.update(adminResource);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public AdminResource findById(String id)throws ResultException {
		try {
			return adminResourceMapper.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
	
	@Override
	public List<AdminResource> findList(AdminResource adminResource)  throws ResultException {
		try {
			return adminResourceMapper.findList(adminResource);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
	
	@Override
	public List<AdminResource> trees(AdminResource adminResource)  throws ResultException {
		try {
			return adminResourceMapper.trees(adminResource);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
}