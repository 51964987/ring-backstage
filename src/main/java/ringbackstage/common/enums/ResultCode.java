package ringbackstage.common.enums;

public enum ResultCode {
	
	/** 成功 */
	SUCCESS("200",""),
	/** 后台出错 */
	SERVER_ERROR("500","后台出错"),
	
	/** 会话不存在或已超时 */
	SESSION_ERROR("10100","会话不存在或已超时"),
	
	/** 不存在的信息 */
	UNEXISTS_INFO_ERROR("10200","不存在的信息"),
	/** 会话不存在或已超时 */
	UNEXISTS_OFFICE_ERROR("10201","会话不存在或已超时"),
	
	/** 用户名或密码不为能空 */
	USERORPASSORD_EMPTY_ERROR("10202","用户名或密码不为能空"),
	/** 用户名或密码错误 */
	USERORPASSORD_ERROR("10203","用户名或密码错误"),
	
	/** 验证码不为能空 */
	LOGIN_CODE_EMPTY_ERROR("10204","验证码不为能空"),
	/** 验证码输入不正确 */
	LOGIN_CODE_ERROR("10205","验证码输入不正确"),
	
	/** 用户已存在 */
	EXISTS_USER_ERROR("10300","用户已存在"),
	
	/** 参数出错 */
	PARAMETER_ERROR("11100","参数出错");
	
	private String code;
	private String msg;
	
	ResultCode(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
}
