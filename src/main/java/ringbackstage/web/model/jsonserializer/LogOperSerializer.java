package ringbackstage.web.model.jsonserializer;

import java.io.IOException;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 通用序列化
 * @author ring
 * @date 2017年12月22日 下午4:08:48
 * @version V1.0
 */
public class LogOperSerializer extends JsonSerializer<String>{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
		
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
			sb.append("<a style=\"text-decoration:none\" onClick=\"oper_detail(this,'"+idValue+"')\" href=\"javascript:;\" title=\"查看\"><i class=\"Hui-iconfont\">&#xe720;</i></a>");
			jg.writeString(sb.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
}
