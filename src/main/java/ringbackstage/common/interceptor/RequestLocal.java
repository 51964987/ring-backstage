package ringbackstage.common.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import ringbackstage.web.model.user.AdminUser;

/**
 * 线程对象
 * @author ring
 * @date 2017年12月4日 下午4:18:20
 * @version V1.0
 */
public class RequestLocal {
	/** 用户对象 */
	static ThreadLocal<AdminUser> user = new ThreadLocal<>();
	/** 请求对象 */
	static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
	/** 响应开始时间 */
	static ThreadLocal<Date> start = new ThreadLocal<>();
	
	public static ThreadLocal<AdminUser> getUser() {
		return user;
	}
	public static void setUser(ThreadLocal<AdminUser> user) {
		RequestLocal.user = user;
	}
	public static ThreadLocal<HttpServletRequest> getRequest() {
		return request;
	}
	public static void setRequest(ThreadLocal<HttpServletRequest> request) {
		RequestLocal.request = request;
	}
	public static ThreadLocal<Date> getStart() {
		return start;
	}
	public static void setStart(ThreadLocal<Date> start) {
		RequestLocal.start = start;
	}
	
}
