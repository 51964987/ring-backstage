package ringbackstage.web.service.role;

import java.util.List;

import ringbackstage.common.exception.ResultException;
import ringbackstage.web.model.role.AdminRole;
import ringbackstage.web.model.user.AdminUser;

public interface AdminRoleService {
	int enabled(AdminRole adminRole,AdminUser loginUser)throws ResultException;
	int add(AdminRole adminRole,AdminUser loginUser)throws ResultException;
	int delete(AdminRole adminRole,AdminUser loginUser)throws ResultException;
	int update(AdminRole adminRole,AdminUser loginUser)throws ResultException;
	AdminRole findById(String id)throws ResultException;
	List<AdminRole> findList(AdminRole adminRole)throws ResultException;
}
