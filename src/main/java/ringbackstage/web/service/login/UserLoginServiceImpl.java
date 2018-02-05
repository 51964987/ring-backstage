package ringbackstage.web.service.login;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ringbackstage.common.consts.PlatformConstant;
import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.login.UserLoginMapper;
import ringbackstage.web.model.user.AdminUser;
import ringbackstage.web.model.user.AdminUserRole;
import ringbackstage.web.service.role.AdminRoleResourceService;
import ringbackstage.web.service.user.AdminUserRoleService;
import ringutils.encrypt.MD5Util;
@Service
public class UserLoginServiceImpl implements UserLoginService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserLoginMapper userLoginMapper;
	@Autowired
	private AdminUserRoleService adminUserRoleService;
	@Autowired
	private AdminRoleResourceService adminRoleResourceService;
	
	@Override
	public void login(String loginName, String password, HttpServletRequest request) throws ResultException {
		AdminUser loginUser = null;
		if(StringUtils.isEmpty(loginName)|| StringUtils.isEmpty(password)){
			throw new ResultException(ResultCode.USERORPASSORD_EMPTY_ERROR);
		}
		try {
			loginUser = userLoginMapper.findByLoginName(loginName);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR,e.getMessage());
		}
		if(loginUser != null){
			//校验密码
			if(!loginUser.getPassword().equals(MD5Util.encrypt(loginUser.getSalt()+password))){
				throw new ResultException(ResultCode.USERORPASSORD_ERROR);
			}
			//获取用户资源
			request.getSession().setAttribute(PlatformConstant.SESSION_USER, loginUser);
			JSONObject resultData = findUserResource(loginUser,request);
			request.getSession().setAttribute(PlatformConstant.SESSION_RESULTDATA, resultData);
		}else{
			throw new ResultException(ResultCode.USERORPASSORD_ERROR);
		}
	}

	private JSONObject findUserResource(AdminUser loginUser, HttpServletRequest request) throws ResultException {
		JSONObject resultData = new JSONObject();
		
		//用户信息
		JSONObject user = new JSONObject();
		user.put("id",loginUser.getId());
		user.put("officeName",loginUser.getOfficeName());
		user.put("loginName", loginUser.getLoginName());
		user.put("name", loginUser.getName());
		resultData.put("user", user);
		
		//用户拥有的角色
		JSONArray roleArray = new JSONArray();
		Set<String> roleIds = new HashSet<>();;
		AdminUserRole adminUserRole = new AdminUserRole();
		adminUserRole.setUserId(loginUser.getId());
		List<AdminUserRole> userRoles = adminUserRoleService.findList(adminUserRole);
		if(userRoles != null && userRoles.size() > 0){
			JSONObject role = null;
			for(AdminUserRole tmpUserRole : userRoles){
				roleIds.add(tmpUserRole.getRoleId());
				role = new JSONObject();
				role.put("id",tmpUserRole.getRoleId());;
				role.put("name", tmpUserRole.getRoleName());;
				roleArray.add(role);
			}
		}
		resultData.put("role", roleArray);
		
		//角色拥有的业务资源
		JSONArray busResource = adminRoleResourceService.findBusResource(roleIds);
		resultData.put("busResource", busResource);
		
		//角色拥有的后台资源
		JSONArray backResource = adminRoleResourceService.findBackResource(roleIds);
		resultData.put("backResource", backResource);
		
		return resultData;
	}

}
