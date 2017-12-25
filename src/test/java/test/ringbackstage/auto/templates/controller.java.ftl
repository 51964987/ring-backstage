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
import ${beanPackage}.${beanName};
import ${servicePackage}.${serviceClassName};

@RequestMapping({"/${model}"})
@Controller
public class ${controllerClassName} {
	
	@Autowired
	${serviceClassName} ${serviceClassName?uncap_first};
	
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
			data = ${serviceClassName?uncap_first}.add(${beanName?uncap_first},RequestLocal.getUser().get());
		} catch (ResultException e) {
			resultCode = e.getResultCode();
		}
		return ResultGenerator.result(resultCode, data, RequestLocal.getStart().get());
	}

	//--/增加--//
		
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
}