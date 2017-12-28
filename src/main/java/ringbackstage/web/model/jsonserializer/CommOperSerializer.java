package ringbackstage.web.model.jsonserializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ringbackstage.common.interceptor.RequestLocal;

/**
 * 通用序列化
 * @author ring
 * @date 2017年12月22日 下午4:08:48
 * @version V1.0
 */
@SuppressWarnings("serial")
public class CommOperSerializer extends JsonSerializer<String>{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//授权--用于用户页面
//	private List<String> List = new ArrayList<String>(){{
//		add("user");
//	}};
	
	//是否可用--用于角色和用户页面
	private List<String> enabledList = new ArrayList<String>(){{
		add("role");
		add("user");
	}};
	
	@Override
	public void serialize(String value, JsonGenerator jg, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		
		try {
			
			//序列对象
			Object object = jg.getCurrentValue();
			//序列对象字段ID
			Field id = object.getClass().getDeclaredField("id");
			//序列对象字段ID值
			String idValue = "";
			if(id != null){
				id.setAccessible(true);
				idValue = (String) id.get(object);
			}
			
			StringBuffer sb = new StringBuffer();
			
			//解析路径
			HttpServletRequest request = RequestLocal.getRequest().get();
			if(request != null){
				//正则解析路径
				Pattern p = Pattern.compile("/([a-zA-Z]*)/.*");
				Matcher m = p.matcher(request.getRequestURI());
				if(m.find()){
					
					//编辑
					sb.append("<a style=\"text-decoration:none\" onClick=\"oper_edit('编辑','update/"+idValue+"')\" href=\"javascript:;\" title=\"编辑\"><i class=\"Hui-iconfont\">&#xe6df;</i></a>");
					
					//是否可用、授权、分配资源
					sb.append(enabledOper(m.group(1),object, idValue));
					
					//删除
					sb.append("&nbsp;&nbsp;<a style=\"text-decoration:none\" onClick=\"oper_delete(this,'"+idValue+"')\" href=\"javascript:;\" title=\"删除\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>");
				}
			}
			
			jg.writeString(sb.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	private String enabledOper(String model,Object object,String idValue) throws Exception{
		StringBuffer sb = new StringBuffer();
		if(enabledList.contains(model)){			
			//序列对象”是否可用“字段
			Field enable = object.getClass().getDeclaredField("enabled");
			if(enable != null){
				enable.setAccessible(true);
				String enableValue = (String) enable.get(object);
				
				//弹出
				String treeTitle = "";
				if("user".equals(model)){
					treeTitle = "授权";
				}else if("role".equals(model)){
					treeTitle = "分配资源";
				}
				sb.append("&nbsp;&nbsp;<a style=\"text-decoration:none\" onClick=\"oper_tree(this,'"+idValue+"')\" href=\"javascript:;\" title=\""+treeTitle+"\"><i class=\"Hui-iconfont\">&#xe63c;</i></a>");
				
				sb.append("&nbsp;&nbsp;");
				if("1".equals(enableValue)){//可用时，设置为停用			
					sb.append("<a style=\"text-decoration:none\" onClick=\"oper_enabled(this,'unusable/"+idValue+"')\" href=\"javascript:;\" title=\"停用\"><i class=\"Hui-iconfont\">&#xe631;</i></a>");
				}else{			
					sb.append("<a style=\"text-decoration:none\" onClick=\"oper_enabled(this,'usable/"+idValue+"')\" href=\"javascript:;\" title=\"启用\"><i class=\"Hui-iconfont\">&#xe615;</i></a>");
				}
			}
		}
		return sb.toString();
	}

}
