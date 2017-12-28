package ringbackstage.web.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ringbackstage.web.model.user.AdminUser;

@Mapper
public interface AdminUserMapper {
	int enabled(AdminUser adminUserMapper)throws Exception;
	int add(AdminUser adminUserMapper)throws Exception;
	int delete(AdminUser adminUserMapper)throws Exception;
	int update(AdminUser adminUserMapper)throws Exception;
	AdminUser findById(String id)throws Exception;
	List<AdminUser> findList(AdminUser adminUserMapper)throws Exception;
	List<AdminUser> trees(AdminUser adminUserMapper)throws Exception;
}
