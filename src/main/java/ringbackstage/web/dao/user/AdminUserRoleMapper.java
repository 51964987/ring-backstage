package ringbackstage.web.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ringbackstage.web.model.user.AdminUserRole;

@Mapper
public interface AdminUserRoleMapper {
	int add(List<AdminUserRole> userRoles)throws Exception;
	int deleteByUserId(String userId)throws Exception;
	List<AdminUserRole> findList(AdminUserRole adminUserRole)throws Exception;
}
