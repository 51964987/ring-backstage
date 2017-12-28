package ringbackstage.web.service.role;

import java.util.List;

import ringbackstage.common.exception.ResultException;
import ringbackstage.web.model.role.AdminRoleResource;

public interface AdminRoleResourceService {
	int accredit(String roleId,List<AdminRoleResource> roleResources)	throws ResultException;
	List<AdminRoleResource> findList(AdminRoleResource adminRoleResource)throws ResultException;
}
