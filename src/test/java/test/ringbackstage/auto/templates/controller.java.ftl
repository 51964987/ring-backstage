package ${controllerPackage};

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
import ${modelPackage}.${beanName};
import ${servicePackage}.${serviceClassName};
<#if isCreateTree == true>
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
</#if>

@RequestMapping({"/${model}"})
@Controller
public class ${controllerClassName} {
	
	@Autowired
	${serviceClassName} ${serviceClassName?uncap_first};
	<#if isCreateADUQ == true>
	//--增加--//
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Map<String , Object> model){
		model.put("${model}", null);
		model.put("url", "add");
		return "${model}/${model}oper";
	}
	
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Object addOper(${beanName} ${beanName?uncap_first}){
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			<#if isCreateTree == true>setParentIds(${beanName?uncap_first});</#if>
			data = ${serviceClassName?uncap_first}.add(${beanName?uncap_first},RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}

	//--/增加--//
	<#if isCreateTree == true>
	private void setParentIds(${beanName} ${beanName?uncap_first}) throws ResultException {
		if(StringUtils.isNotEmpty(${beanName?uncap_first}.getParentId())){
			${beanName} parent = ${serviceClassName?uncap_first}.findById(${beanName?uncap_first}.getParentId());
			if(StringUtils.isEmpty(parent.getParentIds())){
				${beanName?uncap_first}.setParentIds(${beanName?uncap_first}.getParentId());
			}else{
				${beanName?uncap_first}.setParentIds(StringUtils.join(${beanName?uncap_first}.getParentId(),",",parent.getParentIds()));
			}
		}
	}	
	</#if>
	//--删除--//
	@ResponseBody
	@RequestMapping(value="delete/{id}",method=RequestMethod.POST)
	public Object delete(@PathVariable String id){
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			${beanName} ${beanName?uncap_first} = ${serviceClassName?uncap_first}.findById(id);
			if(${beanName?uncap_first} != null){				
				data = ${serviceClassName?uncap_first}.delete(${beanName?uncap_first},RequestLocal.getUser().get());
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
		model.put("${model}", ${serviceClassName?uncap_first}.findById(id));
		model.put("url", "update");
		return "${model}/${model}oper";
	}
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Object update(${beanName} ${beanName?uncap_first}){
		ResultCode resultCode = ResultCode.SUCCESS;
		int data = 0;
		try {
			<#if isCreateTree == true>setParentIds(${beanName?uncap_first});</#if>
			data = ${serviceClassName?uncap_first}.update(${beanName?uncap_first},RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}
	//--/修改--//
	
	//--未删除的列表--//
	@RequestMapping({"/index"})
	public String index(){
		return "${model}/${model}index";
	}
	
	@ResponseBody
	@RequestMapping({"/find${model}"})
	public Object findList(
			@RequestParam(required=false)Integer sEcho,
			@RequestParam(required=false)Integer iDisplayStart,
			@RequestParam(required=false)Integer iDisplayLength,
			${beanName} ${beanName?uncap_first}
			) throws ResultException{
		PageHelper.offsetPage(iDisplayStart, iDisplayLength, true);
		List<${beanName}> list = ${serviceClassName?uncap_first}.findList(${beanName?uncap_first});
		PageInfo<${beanName}> pageInfo = new PageInfo<>(list);
		return DataTableResultHelper.dataTableResult(sEcho+1, pageInfo);
	}
	//--/未删除的列表--//
	</#if><#if isCreateTree == true>
	//--左边树形--//	
	@ResponseBody
	@RequestMapping({"/trees"})
	public Object trees(${beanName} ${beanName?uncap_first}) throws ResultException{
		
		if("-1".equals(${beanName?uncap_first}.getParentId())){
			${beanName?uncap_first}.setParentId("");
		}
		
		List<${beanName}> list = ${serviceClassName?uncap_first}.trees(${beanName?uncap_first});
		List<Map<String, Object>> ztreeDatas = new ArrayList<>();
		Map<String, Object> data = null;
		if(list!= null && list.size()>0){
			for(${beanName} ao : list){
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
				
		List<${beanName}> list = ${serviceClassName?uncap_first}.trees(null);
		List<Map<String, Object>> ztreeDatas = new ArrayList<>();
		Map<String, Object> data = null;
		if(list!= null && list.size()>0){
			for(${beanName} ao : list){
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
				
				${beanName} ar = new ${beanName}();
				ar.setParentId(ao.getId());
				findchild(id, parentId, ar, data, ztreeDatas);
			}
		}
		return ztreeDatas;
	}
	
	public Object findchild(String id,String parentId,${beanName} resource,
			Map<String, Object> parentData,List<Map<String, Object>> ztreeDatas) throws ResultException{
		
		Map<String, Object> data = null;
		List<${beanName}> dataResult = ${serviceClassName?uncap_first}.trees(resource);
		if(dataResult!= null && dataResult.size()>0){
			parentData.put("isParent", true);
			for(${beanName} ao : dataResult){
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
				
				${beanName} ar = new ${beanName}();
				ar.setParentId(ao.getId());
				findchild(id, parentId, ar, data, ztreeDatas);
			}
		}else{
			parentData.put("isParent", false);
		}
		return ztreeDatas;
	}
	//--/下拉菜单树形，用于增加和修改页面--//	
	</#if>
}