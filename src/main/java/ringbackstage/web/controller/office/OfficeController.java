package ringbackstage.web.controller.office;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import ringbackstage.web.model.office.AdminOffice;
import ringbackstage.web.service.OfficeService;

@RequestMapping({"/office"})
@Controller
public class OfficeController {
	
	@Autowired
	OfficeService officeService;
	
	//--增加--//
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Map<String , Object> model){
		model.put("office", null);
		model.put("url", "add");
		return "office/officeoper";
	}
	
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Object addOper(AdminOffice office){
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			setParentIds(office);
			data = officeService.add(office,RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}

	//--/增加--//
	
	private void setParentIds(AdminOffice office) throws ResultException {
		if(StringUtils.isNotEmpty(office.getParentId())){
			AdminOffice parent = officeService.findById(office.getParentId());
			if(StringUtils.isEmpty(parent.getParentIds())){
				office.setParentIds(office.getParentId());
			}else{
				office.setParentIds(StringUtils.join(office.getParentId(),",",parent.getParentIds()));
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
			AdminOffice office = officeService.findById(id);
			if(office != null){				
				data = officeService.delete(office,RequestLocal.getUser().get());
			}else{
				resultCode = ResultCode.UNEXISTS_OFFICE_ERROR;
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
		model.put("office", officeService.findById(id));
		model.put("url", "update");
		return "office/officeoper";
	}
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Object update(AdminOffice office){
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			setParentIds(office);
			data = officeService.update(office,RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}
	//--/修改--//
	
	//--未删除的列表--//
	@RequestMapping({"/index"})
	public String index(){
		return "office/officeindex";
	}
	
	@ResponseBody
	@RequestMapping({"/findoffice"})
	public Object findList(
			@RequestParam(required=false)Integer sEcho,
			@RequestParam(required=false)Integer iDisplayStart,
			@RequestParam(required=false)Integer iDisplayLength,
			AdminOffice office
			) throws ResultException{
		PageHelper.offsetPage(iDisplayStart, iDisplayLength, true);
		List<AdminOffice> list = officeService.findList(office);
		PageInfo<AdminOffice> pageInfo = new PageInfo<>(list);
		return DataTableResultHelper.dataTableResult(sEcho+1, pageInfo);
	}
	//--/未删除的列表--//
	
	//--左边树形--//	
	@ResponseBody
	@RequestMapping({"/trees"})
	public Object trees(AdminOffice office) throws ResultException{
		
		if("-1".equals(office.getParentId())){
			office.setParentId("");
		}
		
		List<AdminOffice> list = officeService.trees(office);
		List<Map<String, Object>> ztreeDatas = new ArrayList<>();
		Map<String, Object> data = null;
		if(list!= null && list.size()>0){
			for(AdminOffice ao : list){
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
				
		List<AdminOffice> list = officeService.trees(null);
		List<Map<String, Object>> ztreeDatas = new ArrayList<>();
		Map<String, Object> data = null;
		if(list!= null && list.size()>0){
			for(AdminOffice ao : list){
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
				
				AdminOffice ar = new AdminOffice();
				ar.setParentId(ao.getId());
				findchild(id, parentId, ar, data, ztreeDatas);
			}
		}
		return ztreeDatas;
	}
	
	public Object findchild(String id,String parentId,AdminOffice resource,
			Map<String, Object> parentData,List<Map<String, Object>> ztreeDatas) throws ResultException{
		
		Map<String, Object> data = null;
		List<AdminOffice> dataResult = officeService.trees(resource);
		if(dataResult!= null && dataResult.size()>0){
			parentData.put("isParent", true);
			for(AdminOffice ao : dataResult){
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
				
				AdminOffice ar = new AdminOffice();
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
