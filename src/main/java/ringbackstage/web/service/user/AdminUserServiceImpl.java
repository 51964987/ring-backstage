package ringbackstage.web.service.user;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.user.AdminUserMapper;
import ringbackstage.web.model.user.AdminUser;

@Service
public class AdminUserServiceImpl implements AdminUserService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AdminUserMapper adminUserMapper;

	@Override
	public int add(AdminUser adminUser,AdminUser loginUser) throws ResultException {
		try {
			adminUser.setDelFlag("0");
			adminUser.setCreateBy(loginUser.getLoginName());
			adminUser.setCreateDate(new Date());
			adminUser.setId(UUID.randomUUID().toString());
			return adminUserMapper.add(adminUser);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			ResultCode error = ResultCode.SERVER_ERROR;
			if(e instanceof DuplicateKeyException){
				error = ResultCode.EXISTS_USER_ERROR;
			}
			throw new ResultException(error);
		}
	}

	@Override
	public int delete(AdminUser adminUser,AdminUser loginUser) throws ResultException {
		try {
			adminUser.setUpdateBy(loginUser.getLoginName());
			adminUser.setUpdateDate(new Date());
			return adminUserMapper.delete(adminUser);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int update(AdminUser adminUser,AdminUser loginUser) throws ResultException {
		try {
			adminUser.setUpdateBy(loginUser.getLoginName());
			adminUser.setUpdateDate(new Date());
			return adminUserMapper.update(adminUser);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public AdminUser findById(String id)throws ResultException {
		try {
			return adminUserMapper.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
	
	@Override
	public List<AdminUser> findList(AdminUser adminUser)  throws ResultException {
		try {
			return adminUserMapper.findList(adminUser);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
}