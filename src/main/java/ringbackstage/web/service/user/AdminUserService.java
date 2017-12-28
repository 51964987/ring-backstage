package ringbackstage.web.service.user;

import java.util.List;

import ringbackstage.common.exception.ResultException;
import ringbackstage.web.model.user.AdminUser;

public interface AdminUserService {
	int enabled(AdminUser adminUser,AdminUser loginUser)throws ResultException;
	int add(AdminUser adminUser,AdminUser loginUser)throws ResultException;
	int delete(AdminUser adminUser,AdminUser loginUser)throws ResultException;
	int update(AdminUser adminUser,AdminUser loginUser)throws ResultException;
	AdminUser findById(String id)throws ResultException;
	List<AdminUser> findList(AdminUser adminUser)throws ResultException;
}
