package ringbackstage.web.dao.role;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ringbackstage.web.model.role.AdminRoleResource;

@Mapper
public interface AdminRoleResourceMapper {
	int add(List<AdminRoleResource> roleResources)throws Exception;
	int deleteByRoleId(String roleId)throws Exception;
	List<AdminRoleResource> findList(AdminRoleResource adminRoleResource)throws Exception;
}
