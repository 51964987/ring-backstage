package ringbackstage.web.service.role;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;

import ringbackstage.common.exception.ResultException;
import ringbackstage.web.model.role.AdminRoleResource;

public interface AdminRoleResourceService {
	int accredit(String roleId,List<AdminRoleResource> roleResources)	throws ResultException;
	List<AdminRoleResource> findList(AdminRoleResource adminRoleResource)throws ResultException;
	
	JSONArray findBusResource(Set<String> roleIds)throws ResultException;
	JSONArray findBackResource(Set<String> roleIds)throws ResultException;
}
