package ringbackstage.web.service.login;

import javax.servlet.http.HttpServletRequest;

import ringbackstage.common.exception.ResultException;

public interface UserLoginService {
	void login(String loginName,String password,HttpServletRequest request)throws ResultException;
}
