package ringbackstage.web.controller.role;

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
import ringbackstage.web.model.role.AdminRole;
import ringbackstage.web.service.role.AdminRoleService;

@RequestMapping({"/role"})
@Controller
public class AdminRoleController {
	
	@Autowired
	AdminRoleService adminRoleService;
	
	//--启用--//
	@ResponseBody
	@RequestMapping(value="usable/{id}",method=RequestMethod.POST)
	public Object usable(@PathVariable String id){
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			AdminRole adminRole = adminRoleService.findById(id);
			if(adminRole != null){
				adminRole.setEnabled("1");
				data = adminRoleService.enabled(adminRole,RequestLocal.getUser().get());
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
			AdminRole adminRole = adminRoleService.findById(id);
			if(adminRole != null){	
				adminRole.setEnabled("0");
				data = adminRoleService.enabled(adminRole,RequestLocal.getUser().get());
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
		model.put("role", null);
		model.put("url", "add");
		return "role/roleoper";
	}
	
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Object addOper(AdminRole adminRole){
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			
			data = adminRoleService.add(adminRole,RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
			errorMsg = e.getErrorMessage();
		}
		return ResultGenerator.result(resultCode,errorMsg, data, RequestLocal.getStart().get());
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
			AdminRole adminRole = adminRoleService.findById(id);
			if(adminRole != null){				
				data = adminRoleService.delete(adminRole,RequestLocal.getUser().get());
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
		model.put("role", adminRoleService.findById(id));
		model.put("url", "update");
		return "role/roleoper";
	}
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Object update(AdminRole adminRole){
		ResultCode resultCode = ResultCode.SUCCESS;
		String errorMsg = null;
		int data = 0;
		try {
			data = adminRoleService.update(adminRole,RequestLocal.getUser().get());
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
		return "role/roleindex";
	}
	
	@ResponseBody
	@RequestMapping({"/findrole"})
	public Object findList(
			@RequestParam(required=false)Integer sEcho,
			@RequestParam(required=false)Integer iDisplayStart,
			@RequestParam(required=false)Integer iDisplayLength,
			AdminRole adminRole
			) throws ResultException{
		Map<String, Object> map = new HashMap<>();
		try {
			PageHelper.offsetPage(iDisplayStart, iDisplayLength, true);
			List<AdminRole> list = adminRoleService.findList(adminRole);
			PageInfo<AdminRole> pageInfo = new PageInfo<>(list);
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