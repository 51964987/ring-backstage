package ringbackstage.web.service.log;

import java.util.List;

import ringbackstage.common.exception.ResultException;
import ringbackstage.web.model.log.AdminLog;
import ringbackstage.web.model.user.AdminUser;

public interface AdminLogService {
	int add(AdminLog adminLog,AdminUser loginUser)throws ResultException;
	AdminLog findById(String id)throws ResultException;
	List<AdminLog> findList(AdminLog adminLog)throws ResultException;
	
}
