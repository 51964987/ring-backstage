package ringbackstage.web.service.resource;

import java.util.List;

import ringbackstage.common.exception.ResultException;
import ringbackstage.web.model.resource.AdminResource;
import ringbackstage.web.model.user.AdminUser;

public interface AdminResourceService {
	int add(AdminResource adminResource,AdminUser loginUser)throws ResultException;
	int delete(AdminResource adminResource,AdminUser loginUser)throws ResultException;
	int update(AdminResource adminResource,AdminUser loginUser)throws ResultException;
	AdminResource findById(String id)throws ResultException;
	List<AdminResource> findList(AdminResource adminResource)throws ResultException;
	List<AdminResource> trees(AdminResource adminResource)throws ResultException;
	List<AdminResource> findTree(AdminResource adminResource)throws ResultException;
}
