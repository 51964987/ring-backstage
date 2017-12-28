package ringbackstage.web.controller.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.common.interceptor.RequestLocal;
import ringbackstage.common.utils.result.ResultGenerator;
import ringbackstage.web.model.resource.AdminResource;
import ringbackstage.web.model.role.AdminRoleResource;
import ringbackstage.web.service.resource.AdminResourceService;
import ringbackstage.web.service.role.AdminRoleResourceService;

@Controller
public class AdminRoleResourceController {
	
	@Autowired
	AdminRoleResourceService adminRoleResourceService;
	@Autowired
	AdminResourceService adminResourceService;
	
	//--角色资源--//
	@RequestMapping(value="roleresource",method=RequestMethod.GET)
	public String add(Map<String , Object> model){
		return "role/roleresourceindex";
	}
	@ResponseBody
	@RequestMapping(value="roleresource/accredit",method=RequestMethod.POST)
	public Object accredit(String id,String[] resourceId) throws ResultException{
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			List<AdminRoleResource> userRoles = new ArrayList<>();
			if(resourceId != null && resourceId.length > 0){
				AdminRoleResource roleResource = null;
				for(String rid : resourceId){
					roleResource = new AdminRoleResource();
					roleResource.setRoleId(id);
					roleResource.setResourceId(rid);
					userRoles.add(roleResource);
				}
			}
			adminRoleResourceService.accredit(id, userRoles);	
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}
	//--/角色资源--//
	
	//--树形--//	
	@ResponseBody
	@RequestMapping({"roleresource/trees"})
	public Object trees(String id) throws ResultException{//角色ID
		
		List<Map<String, Object>> ztreeDatas = new ArrayList<>();
		Map<String, Object> data = null;
		
		if(StringUtils.isEmpty(id)){
			throw new ResultException(ResultCode.UNEXISTS_INFO_ERROR);
		}
		
		//角色所有的资源
		AdminRoleResource adminRoleResource = new AdminRoleResource();
		adminRoleResource.setRoleId(id);
		List<AdminRoleResource> userRoles = adminRoleResourceService.findList(adminRoleResource);
		Map<String, Boolean> roleMap = new HashMap<>();
		if(userRoles != null && userRoles.size() > 0){
			for(AdminRoleResource roleResource : userRoles){
				roleMap.put(roleResource.getResourceId(), true);
			}
		}
		
		//构造子节点对象
		AdminResource resource = new AdminResource();
		findChildren(resource,ztreeDatas,data,roleMap);
		
		return ztreeDatas;
	}
	//--/树形--//
	private void findChildren(AdminResource resource, List<Map<String, Object>> ztreeDatas, 
			Map<String, Object> parentData,Map<String, Boolean> roleResourcesMap) throws ResultException {
		List<AdminResource > dataResult = adminResourceService.findTree(resource);
		if(dataResult!= null && dataResult.size()>0){
			Map<String, Object> data = null;
			
			//当前所有启用角色
			for(AdminResource ao : dataResult){
				data = new HashMap<>();
				data.put("id", ao.getId());
				data.put("pId", StringUtils.isEmpty(ao.getParentId())?"-1":ao.getParentId());
				data.put("name", ao.getName());
				data.put("isParent",false);
				data.put("open",true);
				data.put("checked",roleResourcesMap.getOrDefault(ao.getId(), false));
				
				ztreeDatas.add(data);
				
				//拥有的资源
				if(parentData != null && parentData.get("checked") != null 
						&& ((boolean)parentData.get("checked")) == false
						&& ((boolean)data.get("checked")) == true){
					parentData.put("checked", true);
				}
				
				//构造子节点对象
				AdminResource aoResource = new AdminResource();
				aoResource.setParentId(ao.getId());
				findChildren(aoResource,ztreeDatas,data,roleResourcesMap);
				
			}
			if(parentData != null){
				parentData.put("isParent",true);
			}
		}else{
			if(parentData != null){
				parentData.put("isParent",false);
			}
		}
	}
	
}