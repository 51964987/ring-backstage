package ringbackstage.web.dao.role;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ringbackstage.web.model.role.AdminRole;

@Mapper
public interface AdminRoleMapper {
	int enabled(AdminRole adminRole)throws Exception;
	int add(AdminRole adminRole)throws Exception;
	int delete(AdminRole adminRole)throws Exception;
	int update(AdminRole adminRole)throws Exception;
	AdminRole findById(String id)throws Exception;
	List<AdminRole> findList(AdminRole adminRole)throws Exception;
	
}
