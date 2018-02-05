package ringbackstage.web.controller.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ringbackstage.common.exception.ResultException;
import ringbackstage.common.utils.page.DataTableResultHelper;
import ringbackstage.web.model.log.AdminLog;
import ringbackstage.web.service.log.AdminLogService;

@RequestMapping({"/log"})
@Controller
public class AdminLogController {
	
	@Autowired
	AdminLogService adminLogService;
		
	//--未删除的列表--//
	@RequestMapping({"/index"})
	public String index(){
		return "log/logindex";
	}
	
	@ResponseBody
	@RequestMapping({"/findlog"})
	public Object findList(
			@RequestParam(required=false)Integer sEcho,
			@RequestParam(required=false)Integer iDisplayStart,
			@RequestParam(required=false)Integer iDisplayLength,
			AdminLog adminLog
			) throws ResultException{
		PageHelper.offsetPage(iDisplayStart, iDisplayLength, true);
		List<AdminLog> list = adminLogService.findList(adminLog);
		PageInfo<AdminLog> pageInfo = new PageInfo<>(list);
		return DataTableResultHelper.dataTableResult(sEcho+1, pageInfo);
	}
	//--/未删除的列表--//
}