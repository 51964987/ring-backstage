package ringbackstage.common.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 静态变量 保存 spring的ApplicationContext容器  方便其他地方 使用  获取Bean
 * @author YU
 *2016 08 22
 */
public class SpringContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext context;

	
	@Override
	public void setApplicationContext(ApplicationContext context)throws BeansException {
		SpringContextHolder.context = context;
		
	}
	/**
	 * 获取 ApplicationContext 容器  YU
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		return context;
	}
	
	/**
	 * 获取 bean  YU 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {

		return  context.getBean(name);
	}

}
