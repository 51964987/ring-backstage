package ringbackstage.common.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ringbackstage.common.consts.PlatformConstant;
import ringbackstage.web.model.user.AdminUser;

/**
 * 权限拦截器 
 * @author ring
 * @date 2017年12月4日 下午4:14:48
 * @version V1.0
 */
public class AuthInterceptor implements HandlerInterceptor {

	/**
	 * preHandle返回值为true时调用，用于清理资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	/**
	 * Controller方法处理后调用
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	/**
	 * Controller方法处理前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		System.out.println("url:"+request.getRequestURI());
		
		RequestLocal.request.set(request);
		RequestLocal.start.set(new Date());
		
		//会话对象
		AdminUser user = (AdminUser) request.getSession().getAttribute(PlatformConstant.SESSION_USER);
		if(user == null){
			/*FlashMap flashMap = new FlashMap();
			flashMap.put("result", ResultCode.SESSION_ERROR);
			FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
			flashMapManager.saveOutputFlashMap(flashMap, request, response);
			request.getRequestDispatcher("/msgerror").forward(request, response);
			return false;*/
			//debug
			user = new AdminUser();
			user.setLoginName("admin");
		}
		RequestLocal.getUser().set(user);
		return true;
	}

}
