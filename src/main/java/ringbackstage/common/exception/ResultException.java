package ringbackstage.common.exception;

import ringbackstage.common.enums.ResultCode;
/**
 * 自定义统一异常 
 * @author ring
 * @date 2017年12月4日 下午4:13:13
 * @version V1.0
 */
public class ResultException extends Exception {
	private static final long serialVersionUID = 1L;
	private ResultCode resultCode;
	private String errorMessage;
	
	public ResultException(ResultCode resultCode) {
		super();
		this.resultCode = resultCode;
	}
	public ResultException(ResultCode resultCode,String errorMessage){
		super(resultCode.getMsg());
		this.resultCode = resultCode;
		this.errorMessage = errorMessage;
	}
	
	public ResultCode getResultCode() {
		return resultCode;
	}
	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
