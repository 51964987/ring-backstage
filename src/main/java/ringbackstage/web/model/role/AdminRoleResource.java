package ringbackstage.web.model.role;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ringbackstage.web.model.jsonserializer.CommOperSerializer;
/**
 * 角色-资源
 * @author ring
 * @date 2017年12月26日 17:03:12
 * @version V1.0
 */
public class AdminRoleResource implements Serializable {

	private static final long serialVersionUID = 1L;
	private String roleId;
	private String resourceId;
	@JsonSerialize(using=CommOperSerializer.class)
	private String oper;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
}