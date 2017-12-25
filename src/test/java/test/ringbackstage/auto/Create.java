package test.ringbackstage.auto;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import ringutils.code.ProcessFile;
import ringutils.jdbc.JDBCUtil;
import ringutils.jdbc.impl.JDBCMetaHelper;
import ringutils.string.StringUtil;

public class Create {
	
	private String driver;
	private String url;
	private String username;
	private String password;
	private String charset;
	
	private ProcessFile processFile;
	private Map<String, Object> data;
	
	@Before
	public void before(){
		driver = "spring.datasource.driverClassName";
		url = "spring.datasource.url";
		username = "spring.datasource.username";
		password = "spring.datasource.password";
		charset = "UTF-8";
	}
	
	@Test
	public void mapper() throws Exception{
		//1.JDBC
		JDBCUtil.init("application.properties", driver,url,username,password);
		data = new HashMap<>();
		
		String model = "resource";
		String modelName = "资源";
		String tableName = "admin_resource";
		
		String beanName = StringUtils.capitalize(StringUtil.underline2capitalize(tableName));
		List<JSONObject> cols = JDBCMetaHelper.listColumns(tableName);
		//System.out.println(JSON.toJSONString(cols,true));
		//2.ProcessFile对象
		processFile = new ProcessFile();
		File templateFile = new File(Create.class.getResource("").getPath());
		processFile.initConfig(templateFile.getAbsolutePath()+File.separator+"templates",charset);
		
		//参数
		data.put("tableName", tableName);
		data.put("beanName", beanName);
		data.put("model", model);
		data.put("modelName", modelName);
		data.put("cols", cols);
		data.put("now", DateFormatUtils.format(new Date(),"yyyy年MM月dd日 HH:mm:ss"));
		
		String packagePath = "ringbackstage.web.{0}."+model;
		
		//参数-controller.java文件
		String controllerPackage = packagePath.replace("{0}", "controller");
		String controllerName = beanName+"Controller";
		String controllerPath = processFile.getJavaPath()+File.separator+controllerPackage.replace(".", File.separator);
		String controllerOutputPath =  controllerPath+File.separator+controllerName+".java";
		data.put("controllerPackage", controllerPackage);
		data.put("controllerClassName", controllerName);
		
		//参数-Service.java文件
		String servicePackage = packagePath.replace("{0}", "service");
		String ServiceName = beanName+"Service";
		String servicePath = processFile.getJavaPath()+File.separator+servicePackage.replace(".", File.separator);
		String serviceOutputPath =  servicePath+File.separator+ServiceName+".java";
		data.put("servicePackage", servicePackage);
		data.put("serviceClassName", ServiceName);
		
		//参数-ServiceImpl.java文件
		String ServiceImplName = beanName+"ServiceImpl";
		String serviceImplOutputPath =  servicePath+File.separator+ServiceImplName+".java";
		data.put("serviceImplClassName", ServiceImplName);
		
		//参数-bean.java文件
		String beanPackage = packagePath.replace("{0}", "model");
		//String mapperClassName = beanName+"Mapper";
		String beanPath = processFile.getJavaPath()+File.separator+beanPackage.replace(".", File.separator);
		String beanOutputPath =  beanPath+File.separator+beanName+".java";
		data.put("beanPackage", beanPackage);
		
		//参数-mapper.java文件
		String mapperPackage = packagePath.replace("{0}", "dao");
		String mapperClassName = beanName+"Mapper";
		String mapperPath = processFile.getJavaPath()+File.separator+mapperPackage.replace(".", File.separator);
		String mapperDaoOutputPath =  mapperPath+File.separator+mapperClassName+".java";
		data.put("mapperPackage", mapperPackage);
		data.put("mapperClassName", mapperClassName);
		
		//controller.java
		processFile.templateProcess(data , "controller.java.ftl", controllerOutputPath);
		
		//service.java
		processFile.templateProcess(data , "service.java.ftl", serviceOutputPath);
		
		//serviceImpl.java
		processFile.templateProcess(data , "serviceImpl.java.ftl", serviceImplOutputPath);
		
		//bean.java
		processFile.templateProcess(data , "javabean.java.ftl", beanOutputPath);
		
		//mapper.java
		processFile.templateProcess(data , "mapper.java.ftl", mapperDaoOutputPath);
		
		//mapper.xml
		String mapperXmlOutputPath = processFile.getResourcesPath()+File.separator+"mapper"+File.separator+model+File.separator+mapperClassName+".xml";
		processFile.templateProcess(data , "mapper.xml.ftl", mapperXmlOutputPath);
		
		//index.ftl
		String indexFtlOutputPath = processFile.getWebappPath()+File.separator+"WEB-INF"+File.separator+"templates"+File.separator+model+File.separator+model+"index.ftl";
		processFile.templateProcess(data , "index.ftl.ftl", indexFtlOutputPath);
		String operFtlOutputPath = processFile.getWebappPath()+File.separator+"WEB-INF"+File.separator+"templates"+File.separator+model+File.separator+model+"oper.ftl";
		processFile.templateProcess(data , "oper.ftl.ftl", operFtlOutputPath);
		
	}
	
}
