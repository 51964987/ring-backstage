package ringbackstage.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ringbackstage.common.interceptor.SpringContextHolder;

@Configuration
public class SpringBeanConfig {
	
	@Bean
	public SpringContextHolder springContextHolder(){
		return new SpringContextHolder();
	}
}
