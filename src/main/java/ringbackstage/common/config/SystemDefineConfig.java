package ringbackstage.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 测试样例 
 * @author ring
 * @date 2018年1月23日 下午3:13:05
 * @version V1.0
 */
@Configuration  
@ConfigurationProperties(prefix = "system")  
@PropertySource("classpath:system-define.properties")
public class SystemDefineConfig {
	
	public static Map<String, String> image = new HashMap<>();    
	public static List<String> exCulRecLog = new ArrayList<>();   
	public static List<String> exCulParam = new ArrayList<>();   
	
	//注意需要添加get/set方法，并且这两个方法不能为static   
	public Map<String, String> getImage() {
		return image;
	}
	public void setImage(Map<String, String> image) {
		SystemDefineConfig.image = image;
	}
	public List<String> getExCulRecLog() {
		return exCulRecLog;
	}
	public void setExCulRecLog(List<String> exCulRecLog) {
		SystemDefineConfig.exCulRecLog = exCulRecLog;
	}
	public List<String> getExCulParam() {
		return exCulParam;
	}
	public void setExCulParam(List<String> exCulParam) {
		SystemDefineConfig.exCulParam = exCulParam;
	}	
	
}
