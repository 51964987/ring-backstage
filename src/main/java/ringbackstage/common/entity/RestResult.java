package ringbackstage.common.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 统一返回结果
 * @param <T> 
 * @author ring
 * @date 2017年12月4日 下午3:51:57
 * @version V1.0
 */
public class RestResult<T> {
	private String code;
	private String message;
	private T data;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date start;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date end;
	
	public RestResult() {
		super();
	}
	public static <T> RestResult<T> newInstance(){
		return new RestResult<T>();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
}
