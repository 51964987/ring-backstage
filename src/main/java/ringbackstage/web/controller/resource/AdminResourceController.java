package ringbackstage.web.controller.resource;

import java.util.ArrayList;
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
import ringbackstage.web.model.resource.AdminResource;
import ringbackstage.web.service.resource.AdminResourceService;
import org.apache.commons.lang3.StringUtils;

@RequestMapping({"/resource"})
@Controller
public class AdminResourceController {
	
	@Autowired
	AdminResourceService adminResourceService;
	
	//--增加--//
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Map<String , Object> model){
		model.put("resource", null);
		model.put("url", "add");
		return "resource/resourceoper";
	}
	
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Object addOper(AdminResource adminResource){
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			setParentIds(adminResource);
			data = adminResourceService.add(adminResource,RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}

	//--/增加--//
	private void setParentIds(AdminResource adminResource) throws ResultException {
		if(StringUtils.isNotEmpty(adminResource.getParentId())){
			AdminResource parent = adminResourceService.findById(adminResource.getParentId());
			if(StringUtils.isEmpty(parent.getParentIds())){
				adminResource.setParentIds(adminResource.getParentId());
			}else{
				adminResource.setParentIds(StringUtils.join(adminResource.getParentId(),",",parent.getParentIds()));
			}
		}
	}	
	//--删除--//
	@ResponseBody
	@RequestMapping(value="delete/{id}",method=RequestMethod.POST)
	public Object delete(@PathVariable String id){
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			AdminResource adminResource = adminResourceService.findById(id);
			if(adminResource != null){				
				data = adminResourceService.delete(adminResource,RequestLocal.getUser().get());
			}else{
				resultCode = ResultCode.UNEXISTS_INFO_ERROR;
			}
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}
	//--/删除--//
	
	//--修改--//
	@RequestMapping(value="update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id,Map<String , Object> model) throws ResultException{
		model.put("resource", adminResourceService.findById(id));
		model.put("url", "update");
		return "resource/resourceoper";
	}
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Object update(AdminResource adminResource){
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			setParentIds(adminResource);
			data = adminResourceService.update(adminResource,RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}
	//--/修改--//
	
	//--未删除的列表--//
	@RequestMapping({"/index"})
	public String index(){
		return "resource/resourceindex";
	}
	
	@ResponseBody
	@RequestMapping({"/findresource"})
	public Object findList(
			@RequestParam(required=false)Integer sEcho,
			@RequestParam(required=false)Integer iDisplayStart,
			@RequestParam(required=false)Integer iDisplayLength,
			AdminResource adminResource
			) throws ResultException{
		PageHelper.offsetPage(iDisplayStart, iDisplayLength, true);
		List<AdminResource> list = adminResourceService.findList(adminResource);
		PageInfo<AdminResource> pageInfo = new PageInfo<>(list);
		return DataTableResultHelper.dataTableResult(sEcho+1, pageInfo);
	}
	//--/未删除的列表--//
	
	//--左边树形--//	
	@ResponseBody
	@RequestMapping({"/trees"})
	public Object trees(AdminResource adminResource) throws ResultException{
		
		if("-1".equals(adminResource.getParentId())){
			adminResource.setParentId("");
		}
		
		List<AdminResource> list = adminResourceService.trees(adminResource);
		List<Map<String, Object>> ztreeDatas = new ArrayList<>();
		Map<String, Object> data = null;
		if(list!= null && list.size()>0){
			for(AdminResource ao : list){
				data = new HashMap<>();
				data.put("id", ao.getId());
				data.put("pId", StringUtils.isEmpty(ao.getParentId())?"-1":ao.getParentId());
				data.put("name", ao.getName());
				data.put("isParent",true);
				data.put("open",true);
				
				ztreeDatas.add(data);
			}
		}
		return ztreeDatas;
	}
	//--/左边树形--//
	
	//--下拉菜单树形，用于增加和修改页面--//	
	@ResponseBody
	@RequestMapping({"/selecttree"})
	public Object selecttree(String id,String parentId) throws ResultException{
				
		List<AdminResource> list = adminResourceService.trees(null);
		List<Map<String, Object>> ztreeDatas = new ArrayList<>();
		Map<String, Object> data = null;
		if(list!= null && list.size()>0){
			for(AdminResource ao : list){
				data = new HashMap<>();
				data.put("id", ao.getId());
				data.put("pId", StringUtils.isEmpty(ao.getParentId())?"-1":ao.getParentId());
				data.put("name", ao.getName());
				data.put("isParent",false);
				data.put("open",true);
				data.put("checked",StringUtils.isNotEmpty(parentId)&&parentId.equals(ao.getId()));
				
				if(StringUtils.isEmpty(id) || (StringUtils.isNotEmpty(id) && !id.equals(ao.getId()))){					
					ztreeDatas.add(data);
				}
				
				AdminResource ar = new AdminResource();
				ar.setParentId(ao.getId());
				findchild(id, parentId, ar, data, ztreeDatas);
			}
		}
		return ztreeDatas;
	}
	
	public Object findchild(String id,String parentId,AdminResource resource,
			Map<String, Object> parentData,List<Map<String, Object>> ztreeDatas) throws ResultException{
		
		Map<String, Object> data = null;
		List<AdminResource> dataResult = adminResourceService.trees(resource);
		if(dataResult!= null && dataResult.size()>0){
			parentData.put("isParent", true);
			for(AdminResource ao : dataResult){
				data = new HashMap<>();
				data.put("id", ao.getId());
				data.put("pId", StringUtils.isEmpty(ao.getParentId())?"-1":ao.getParentId());
				data.put("name", ao.getName());
				data.put("isParent",true);
				data.put("open",true);
				data.put("checked",StringUtils.isNotEmpty(parentId)&&parentId.equals(ao.getId()));
				
				if(StringUtils.isEmpty(id) || (StringUtils.isNotEmpty(id) && !id.equals(ao.getId()))){					
					ztreeDatas.add(data);
				}
				
				AdminResource ar = new AdminResource();
				ar.setParentId(ao.getId());
				findchild(id, parentId, ar, data, ztreeDatas);
			}
		}else{
			parentData.put("isParent", false);
		}
		return ztreeDatas;
	}
	//--/下拉菜单树形，用于增加和修改页面--//	
}