package ${modelPackage};

import java.io.Serializable;
<#assign coltmp=""/>
<#list cols as col>
<#if coltmp?index_of(";${col.FIELD_CLASS};") == -1 >
import ${col.FIELD_CLASS};
<#assign coltmp="${coltmp};${col.FIELD_CLASS};"/>
</#if>
</#list>
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ringbackstage.web.model.jsonserializer.CommOperSerializer;
/**
 * ${modelName}
 * @author ring
 * @date ${now}
 * @version V1.0
 */
public class ${beanName} implements Serializable {

	private static final long serialVersionUID = 1L;
	<#list cols as col>
	<#if col.FIELD_TYPE == "Date">
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	</#if>
	<#if col.FIELD_NAME == "password">
	@JsonIgnore
	</#if>
	private ${col.FIELD_TYPE} ${col.FIELD_NAME};
	</#list>
	@JsonSerialize(using=CommOperSerializer.class)
	private String oper;
	
	<#list cols as col>
	public ${col.FIELD_TYPE} get${col.FIELD_NAME?cap_first}() {
		return ${col.FIELD_NAME};
	}
	public void set${col.FIELD_NAME?cap_first}(${col.FIELD_TYPE} ${col.FIELD_NAME}) {
		this.${col.FIELD_NAME} = ${col.FIELD_NAME};
	}
	</#list>
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
}