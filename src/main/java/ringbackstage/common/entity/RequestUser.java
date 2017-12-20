package ringbackstage.common.entity;

import java.io.Serializable;

public class RequestUser implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String loginName;	//登录名
	private String password;	//登录类型
	private String loginType;	//登录类型
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
}
