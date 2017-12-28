package ringbackstage.web.service.role;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.role.AdminRoleMapper;
import ringbackstage.web.model.role.AdminRole;
import ringbackstage.web.model.user.AdminUser;

@Service
public class AdminRoleServiceImpl implements AdminRoleService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AdminRoleMapper adminRoleMapper;

	@Override
	public int enabled(AdminRole adminRole,AdminUser loginUser) throws ResultException {
		try {
			adminRole.setUpdateBy(loginUser.getLoginName());
			adminRole.setUpdateDate(new Date());
			return adminRoleMapper.enabled(adminRole);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
	
	@Override
	public int add(AdminRole adminRole,AdminUser loginUser) throws ResultException {
		try {
			adminRole.setDelFlag("0");
			adminRole.setEnabled("1");
			adminRole.setCreateBy(loginUser.getLoginName());
			adminRole.setCreateDate(new Date());
			adminRole.setId(UUID.randomUUID().toString());
			return adminRoleMapper.add(adminRole);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int delete(AdminRole adminRole,AdminUser loginUser) throws ResultException {
		try {
			adminRole.setUpdateBy(loginUser.getLoginName());
			adminRole.setUpdateDate(new Date());
			return adminRoleMapper.delete(adminRole);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int update(AdminRole adminRole,AdminUser loginUser) throws ResultException {
		try {
			adminRole.setUpdateBy(loginUser.getLoginName());
			adminRole.setUpdateDate(new Date());
			return adminRoleMapper.update(adminRole);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public AdminRole findById(String id)throws ResultException {
		try {
			return adminRoleMapper.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
	
	@Override
	public List<AdminRole> findList(AdminRole adminRole)  throws ResultException {
		try {
			return adminRoleMapper.findList(adminRole);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
}