package ringbackstage.web.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.common.interceptor.RequestLocal;
import ringbackstage.common.utils.page.DataTableResultHelper;
import ringbackstage.common.utils.result.ResultGenerator;
import ringbackstage.web.model.user.AdminUser;
import ringbackstage.web.service.user.AdminUserService;

@RequestMapping({"/user"})
@Controller
public class AdminUserController {
	
	@Autowired
	AdminUserService adminUserService;

	//--启用--//
	@ResponseBody
	@RequestMapping(value="usable/{id}",method=RequestMethod.POST)
	public Object usable(@PathVariable String id){
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			AdminUser adminUser = adminUserService.findById(id);
			if(adminUser != null){
				adminUser.setEnabled("1");
				data = adminUserService.enabled(adminUser,RequestLocal.getUser().get());
			}else{
				resultCode = ResultCode.UNEXISTS_INFO_ERROR;
			}
		} catch (ResultException e) {
			resultCode = e.getResultCode();
			errorMsg = e.getErrorMessage();
		}
		return ResultGenerator.result(resultCode,errorMsg, data, RequestLocal.getStart().get());
	}
	//--/启用--//
	
	//--停用--//
	@ResponseBody
	@RequestMapping(value="unusable/{id}",method=RequestMethod.POST)
	public Object unusable(@PathVariable String id){
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			AdminUser adminUser = adminUserService.findById(id);
			if(adminUser != null){	
				adminUser.setEnabled("0");
				data = adminUserService.enabled(adminUser,RequestLocal.getUser().get());
			}else{
				resultCode = ResultCode.UNEXISTS_INFO_ERROR;
			}
		} catch (ResultException e) {
			resultCode = e.getResultCode();
			errorMsg = e.getErrorMessage();
		}
		return ResultGenerator.result(resultCode,errorMsg, data, RequestLocal.getStart().get());
	}
	//--/停用--//
	
	//--增加--//
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Map<String , Object> model){
		model.put("user", null);
		model.put("url", "add");
		return "user/useroper";
	}
	
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Object addOper(AdminUser adminUser){
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			data = adminUserService.add(adminUser,RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
			errorMsg = e.getErrorMessage();
		}
		return ResultGenerator.result(resultCode, errorMsg,data, RequestLocal.getStart().get());
	}

	//--/增加--//
		
	//--删除--//
	@ResponseBody
	@RequestMapping(value="delete/{id}",method=RequestMethod.POST)
	public Object delete(@PathVariable String id){
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			AdminUser adminUser = adminUserService.findById(id);
			if(adminUser != null){				
				data = adminUserService.delete(adminUser,RequestLocal.getUser().get());
			}else{
				resultCode = ResultCode.UNEXISTS_INFO_ERROR;
			}
		} catch (ResultException e) {
			resultCode = e.getResultCode();
			errorMsg = e.getErrorMessage();
		}
		return ResultGenerator.result(resultCode,errorMsg, data, RequestLocal.getStart().get());
	}
	//--/删除--//
	
	//--修改--//
	@RequestMapping(value="update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id,Map<String , Object> model) throws ResultException{
		model.put("user", adminUserService.findById(id));
		model.put("url", "update");
		return "user/useroper";
	}
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Object update(AdminUser adminUser){
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			data = adminUserService.update(adminUser,RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
			errorMsg = e.getErrorMessage();
		}
		return ResultGenerator.result(resultCode,errorMsg, data, RequestLocal.getStart().get());
	}
	//--/修改--//
	
	//--未删除的列表--//
	@RequestMapping({"/index"})
	public String index(){
		return "user/userindex";
	}
	
	@ResponseBody
	@RequestMapping({"/finduser"})
	public Object findList(
			@RequestParam(required=false)Integer sEcho,
			@RequestParam(required=false)Integer iDisplayStart,
			@RequestParam(required=false)Integer iDisplayLength,
			AdminUser adminUser
			) throws ResultException{
		Map<String, Object> map = new HashMap<>();
		PageInfo<AdminUser> pageInfo=null;
		try {
			PageHelper.offsetPage(iDisplayStart, iDisplayLength, true);
			List<AdminUser> list = adminUserService.findList(adminUser);
			pageInfo = new PageInfo<>(list);
			return DataTableResultHelper.dataTableResult(sEcho+1, pageInfo);
		} catch (ResultException e) {
			map.put("code", e.getResultCode().getCode());
			map.put("message", e.getResultCode().getMsg());
			map.put("errorMessage",e.getErrorMessage());
		}
		return map;
	}
	//--/未删除的列表--//
}