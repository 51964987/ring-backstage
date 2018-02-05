package ringbackstage.web.service.user;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.user.AdminUserRoleMapper;
import ringbackstage.web.model.user.AdminUserRole;

@Service
public class AdminUserRoleServiceImpl implements AdminUserRoleService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AdminUserRoleMapper adminUserRoleMapper;
	
	@Transactional
	@Override
	public int accredit(String userId,List<AdminUserRole> userRoles) throws ResultException {
		try {
			if(StringUtils.isNotEmpty(userId)){
				adminUserRoleMapper.deleteByUserId(userId);
			}
			if(userRoles != null && userRoles.size() > 0){
				return adminUserRoleMapper.add(userRoles);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR,e.getMessage());
		}
		return 0;
	}
	
	@Override
	public List<AdminUserRole> findList(AdminUserRole adminUserRole)throws ResultException {
		try {
			return adminUserRoleMapper.findList(adminUserRole);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR,e.getMessage());
		}
	}
}