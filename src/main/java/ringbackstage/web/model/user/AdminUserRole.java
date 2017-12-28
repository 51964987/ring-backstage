package ringbackstage.web.model.user;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ringbackstage.web.model.jsonserializer.CommOperSerializer;
/**
 * 用户-角色
 * @author ring
 * @date 2017年12月26日 17:03:13
 * @version V1.0
 */
public class AdminUserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String roleId;
	@JsonSerialize(using=CommOperSerializer.class)
	private String oper;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
}