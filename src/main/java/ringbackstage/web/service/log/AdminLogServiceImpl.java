package ringbackstage.web.service.log;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.log.AdminLogMapper;
import ringbackstage.web.model.log.AdminLog;
import ringbackstage.web.model.user.AdminUser;

@Service
public class AdminLogServiceImpl implements AdminLogService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AdminLogMapper adminLogMapper;
	@Override
	public int add(AdminLog adminLog,AdminUser loginUser) throws ResultException {
		try {
			if(loginUser != null){				
				adminLog.setCreateBy(loginUser.getName());
			}
			adminLog.setCreateDate(new Date(System.currentTimeMillis()));
			adminLog.setId(UUID.randomUUID().toString());
			return adminLogMapper.add(adminLog);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR,e.getMessage());
		}
	}

	@Override
	public AdminLog findById(String id)throws ResultException {
		try {
			return adminLogMapper.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR,e.getMessage());
		}
	}
	
	@Override
	public List<AdminLog> findList(AdminLog adminLog)  throws ResultException {
		try {
			return adminLogMapper.findList(adminLog);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR,e.getMessage());
		}
	}
}