package ringbackstage.common.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ringbackstage.common.config.SystemDefineConfig;
import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.common.interceptor.RequestLocal;
import ringbackstage.common.interceptor.SpringContextHolder;
import ringbackstage.web.model.log.AdminLog;
import ringbackstage.web.model.user.AdminUser;
import ringbackstage.web.service.log.AdminLogService;

@Aspect  
@Component  
public class RequestLog {  
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ThreadLocal<Long> start = new ThreadLocal<>();
	//利用线程池 提高效率 减少开销
	private static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    // defined aop pointcut  
    @Pointcut("execution(* ringbackstage.web.controller..*(..))  and @annotation(org.springframework.web.bind.annotation.RequestMapping)")  
    public void controllerLog() {}  
  
    // log all of controller  
    @Before("controllerLog()")  
    public void before(JoinPoint joinPoint) {
    	start.set(System.currentTimeMillis());
    	 MethodSignature signature = (MethodSignature) joinPoint.getSignature();  
         Method method = signature.getMethod(); //获取被拦截的方法  
         String methodName = method.getName(); //获取被拦截的方法名  
         String[] strings = signature.getParameterNames();  
         System.out.println(Arrays.toString(strings)); 
         logger.info(signature.getDeclaringTypeName()+".{}请求开始", methodName);  
    }  
  
    // result of return  
    @AfterReturning(pointcut = "controllerLog()", returning = "retVal")  
    public void after(JoinPoint pjp, Object retVal) {  
    	
    	MethodSignature signature = (MethodSignature) pjp.getSignature();  
        Method method = signature.getMethod(); //获取被拦截的方法  
        String methodName = method.getName(); //获取被拦截的方法名  
    	
    	RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String uri = request.getRequestURI();
        
        if(!SystemDefineConfig.exCulRecLog.contains(uri)){
        	
        	boolean isPattern = false;
        	for(String str : SystemDefineConfig.exCulRecLog){
        		if(str.indexOf("*")>-1 && isPattern == false){
        			isPattern = Pattern.matches(str, uri);
        		}
        	}
        	//添加日志
        	if(!isPattern){
        		Map<String, String[]> params = request.getParameterMap();
        		Map<String, String[]> reqParams = new HashMap<>();
        		if(params != null){
        			Iterator<String> keys = params.keySet().iterator();
        			while(keys.hasNext()){
        				String key = keys.next()+"";
        				String[] value = params.get(key).clone();
        				if("loginPwd".equals(key)){//密码不显示
        					value = new String[]{"******"};
        				}
        				reqParams.put(key,value);
        			}
        		}
        		
        		//异步执行
        		executor.execute(new LogThread(uri, request.getMethod(), 
        				reqParams,retVal, pjp.getArgs(),RequestLocal.getUser().get()));
        	}
        }
                
        long costMs = System.currentTimeMillis() - start.get();  
        logger.info(signature.getDeclaringTypeName()+".{}请求结束，耗时：{}ms", methodName, costMs);  
        
    }
    
    /**
     * 线程保存日志
     * @author ring
     * @date 2018年1月25日 下午1:59:37
     * @version V1.0
     */
    public class LogThread extends Thread{
    	private Logger log = LoggerFactory.getLogger(getClass());
    	
    	private String uri;
    	private String method;
    	private Object retVal;
    	private Map<String, String[]> params;
		private Object[] args;
		private AdminUser loginUser;
		
		public LogThread(String uri, String method,Map<String, String[]> params, 
				Object retVal,Object[] args,AdminUser loginUser) {
			super();
			this.uri = uri;
			this.method = method;
			this.params = params;
			this.retVal = retVal;
			this.args = args;
			this.loginUser = loginUser;
		}

		@Override
		public void run() {
			AdminLogService logService =(AdminLogService) SpringContextHolder.getBean("adminLogServiceImpl");
			AdminLog adminLog = new AdminLog();
			try {
				adminLog.setCode(ResultCode.SUCCESS.getCode());
				adminLog.setMessage(ResultCode.SUCCESS.getMsg());
				adminLog.setReqMethod(method);
				adminLog.setUri(uri);
				
				//重置参数，用于过滤不必要的参数
				resetParams(params);
				adminLog.setReqParams(JSON.toJSONString(params));
				if(retVal!=null &&StringUtils.isNotEmpty(retVal+"") 
						&& (
						(retVal instanceof String) == false 
						&& retVal.getClass().isArray() == false 
						&& (retVal instanceof ArrayList) == false 
						)){//JSON，返回值
					JSONObject retValJSON = JSONObject.parseObject(JSON.toJSONString(retVal));
					adminLog.setCode(retValJSON.getString("code"));
					adminLog.setMessage(retValJSON.getString("message"));
					adminLog.setErrorMsg(retValJSON.getString("errorMsg"));
				}else if(args != null && retVal instanceof String){//页面，传入参
					if(args != null && args.length >0 ){
	        			for(Object obj : args){//遍历入参，找到modelAndView保存的异常参数
	        				if(obj instanceof BindingAwareModelMap){
	        					BindingAwareModelMap map = (BindingAwareModelMap)obj;
	        					ResultException error = (ResultException) map.get("error");
	        					if(error != null){	        						
	        						adminLog.setCode(error.getResultCode().getCode());
	        						adminLog.setMessage(error.getResultCode().getMsg());
	        						adminLog.setErrorMsg(error.getErrorMessage());
	        					}
	        				}
	        			}
	        		}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				adminLog.setErrorMsg(e.getLocalizedMessage());
			}finally{
				try {
					logService.add(adminLog, loginUser);
				} catch (ResultException e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}
			}
		}
		
    }
    
    //重设入参，用于过滤不必要的参数
    private void resetParams(Map<String, String[]> params){
		if(params != null&&params.size()>0){
			SystemDefineConfig.exCulParam.forEach(str->{//不记录的参数
				if(str.indexOf("*")>-1){
					str = str.substring(0, str.length()-1);
					Iterator<String> paramKeys = params.keySet().iterator();
					List<String> removeKeyList = new ArrayList<>();
					while(paramKeys.hasNext()){
						String key = paramKeys.next();
						if(Pattern.matches(str, key)){
							removeKeyList.add(key);
						}
					}
					if(removeKeyList.size()>0){								
						for(String key :removeKeyList ){								
							params.remove(key);
						}
					}
				}else if(params.get(str) != null){
					params.remove(str);
				}
				
			});
		}
    }
    
}  