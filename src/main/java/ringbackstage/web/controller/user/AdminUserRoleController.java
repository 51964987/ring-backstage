package ringbackstage.web.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.common.interceptor.RequestLocal;
import ringbackstage.common.utils.result.ResultGenerator;
import ringbackstage.web.model.role.AdminRole;
import ringbackstage.web.model.user.AdminUserRole;
import ringbackstage.web.service.role.AdminRoleService;
import ringbackstage.web.service.user.AdminUserRoleService;

@Controller
public class AdminUserRoleController {
	
	@Autowired
	AdminUserRoleService adminUserRoleService;
	
	@Autowired
	AdminRoleService adminRoleService;
	
	//--用户角色--//
	@RequestMapping(value="userrole",method=RequestMethod.GET)
	public String add(Map<String , Object> model){
		return "user/userroleindex";
	}
	@ResponseBody
	@RequestMapping(value="userrole/accredit",method=RequestMethod.POST)
	public Object accredit(String id,String[] roleId) throws ResultException{
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			List<AdminUserRole> userRoles = new ArrayList<>();
			if(roleId != null && roleId.length > 0){
				AdminUserRole userRole = null;
				for(String rid : roleId){
					userRole = new AdminUserRole();
					userRole.setUserId(id);
					userRole.setRoleId(rid);
					userRoles.add(userRole);
				}
			}
			adminUserRoleService.accredit(id, userRoles);	
		} catch (ResultException e) {
			resultCode = e.getResultCode();
			errorMsg = e.getErrorMessage();
		}
		return ResultGenerator.result(resultCode,errorMsg, data, RequestLocal.getStart().get());
	}
	//--/用户角色--//
	
	//--树形--//	
	@ResponseBody
	@RequestMapping({"userrole/trees"})
	public Object trees(String id) throws ResultException{//用户ID
		
		AdminRole role = new AdminRole();
		role.setEnabled("1");
		List<AdminRole> dataResult = adminRoleService.findList(role);
		
		List<Map<String, Object>> ztreeDatas = new ArrayList<>();
		Map<String, Object> data = null;
		
		if(dataResult!= null && dataResult.size()>0){

			//用户所有的角色
			AdminUserRole adminUserRole = new AdminUserRole();
			adminUserRole.setUserId(id);
			List<AdminUserRole> userRoles = adminUserRoleService.findList(adminUserRole);
			Map<String, Boolean> roleMap = new HashMap<>();
			if(userRoles != null && userRoles.size() > 0){
				for(AdminUserRole userRole : userRoles){
					roleMap.put(userRole.getRoleId(), true);
				}
			}
			
			//当前所有启用角色
			for(AdminRole ao : dataResult){
				data = new HashMap<>();
				data.put("id", ao.getId());
				data.put("pId", "-1");
				data.put("name", ao.getName());
				data.put("isParent",false);
				data.put("open",true);
				data.put("checked",roleMap.getOrDefault(ao.getId(), false));
				
				ztreeDatas.add(data);
			}
		}
		return ztreeDatas;
	}
	//--/树形--//
}