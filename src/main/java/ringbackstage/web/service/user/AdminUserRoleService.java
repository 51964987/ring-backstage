package ringbackstage.web.service.user;

import java.util.List;

import ringbackstage.common.exception.ResultException;
import ringbackstage.web.model.user.AdminUserRole;

public interface AdminUserRoleService {
	int accredit(String userId,List<AdminUserRole> userRoles)	throws ResultException;
	List<AdminUserRole> findList(AdminUserRole adminUserRole)throws ResultException;
}
