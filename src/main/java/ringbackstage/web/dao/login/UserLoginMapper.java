package ringbackstage.web.dao.login;

import org.apache.ibatis.annotations.Mapper;

import ringbackstage.web.model.user.AdminUser;

@Mapper
public interface UserLoginMapper {
	AdminUser findByLoginName(String loginName)throws Exception;
}
