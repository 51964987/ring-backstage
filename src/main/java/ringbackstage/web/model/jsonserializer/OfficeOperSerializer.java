package ringbackstage.web.model.jsonserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ringbackstage.web.model.office.AdminOffice;

public class OfficeOperSerializer extends JsonSerializer<String>{

	@Override
	public void serialize(String value, JsonGenerator jg, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		AdminOffice object = (AdminOffice) jg.getCurrentValue();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<a style=\"text-decoration:none\" onClick=\"oper_edit(this,'update/"+object.getId()+"')\" href=\"javascript:;\" title=\"编辑\"><i class=\"Hui-iconfont\">&#xe6df;</i></a>");
		sb.append("&nbsp;&nbsp;<a style=\"text-decoration:none\" onClick=\"oper_delete(this,'"+object.getId()+"')\" href=\"javascript:;\" title=\"删除\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>");
		
		jg.writeString(sb.toString());
	}

}
