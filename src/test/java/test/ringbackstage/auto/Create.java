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
	String packagePath;
	
	boolean controller = false;
	boolean service = false;
	boolean mapperDao = false;
	boolean javaBean = false;
	boolean mapperXml = false;
	boolean indexftl = false;
	boolean operftl = false;
	
	@Before
	public void before(){
		//指定properties文件
		driver = "spring.datasource.driverClassName";
		url = "spring.datasource.url";
		username = "spring.datasource.username";
		password = "spring.datasource.password";
		charset = "UTF-8";
		JDBCUtil.init("application.properties", driver,url,username,password);
	}

	@Test
	public void mapper() throws Exception{
		
		controller = true;
		service = true;
		mapperDao = true;
		javaBean = true;
		mapperXml = true;
		indexftl = true;
		operftl = false;
		
		String[] models = {"log"};
		String[] ftlprefixs = {"log"};
		String[] modelNames = {"操作日志"};
		String[] tableNames = {"admin_log"};
		boolean[] isCreateADUQ = {true};	//增删改查
		boolean[] isCreateTrees = {false};	//树形
		for(int i=0;i<tableNames.length;i++){			
			createFile(models[i], modelNames[i], tableNames[i],
					isCreateTrees[i],isCreateADUQ[i],ftlprefixs[i]);
		}
	}
	
	private void putData(Map<String, Object> data,String model,String prefix,String suffix,String fileType){
		String prefixPackage = packagePath.replace("{0}", model);
		String prefixName = data.get("beanName")+StringUtils.capitalize(suffix);
		String prefixPath = processFile.getJavaPath()+File.separator+prefixPackage.replace(".", File.separator);
		String prefixOutputPath =  prefixPath+File.separator+prefixName+"."+fileType;
		data.put(prefix+"Package", prefixPackage);
		data.put(prefix+"ClassName", prefixName);
		data.put(prefix+"OutputPath", prefixOutputPath);
	}
	
	private void createFile(String model,String modelName,String tableName,
			boolean isCreateTree,boolean isCreateADUQ,String ftlprefix) throws Exception{
		//1. 数据表信息
		data = new HashMap<>();
		String beanName = StringUtils.capitalize(StringUtil.underline2capitalize(tableName));
		List<JSONObject> cols = JDBCMetaHelper.listColumns(tableName);

		//2. ProcessFile对象
		processFile = new ProcessFile();
		File templateFile = new File(Create.class.getResource("").getPath());
		processFile.initConfig(templateFile.getAbsolutePath()+File.separator+"templates",charset);
		
		//3.1 参数
		data.put("tableName", tableName);
		data.put("beanName", beanName);
		data.put("model", model);
		data.put("modelName", modelName);
		data.put("cols", cols);
		data.put("now", DateFormatUtils.format(new Date(),"yyyy年MM月dd日 HH:mm:ss"));
		data.put("isCreateTree", isCreateTree);	//用于生成左边树形
		data.put("isCreateADUQ", isCreateADUQ);	//增删改查
		
		//FTL文件名前缀
		String ftpmodel = StringUtils.isEmpty(ftlprefix)?model:ftlprefix;
		
		packagePath = "ringbackstage.web.{0}."+model;
		
		//3.2 参数-controller.java文件
		putData(data, "controller", "controller", "controller", "java");
		
		//3.3 参数-Service.java文件
		putData(data, "service", "service", "service", "java");
		
		//3.4 参数-ServiceImpl.java文件
		putData(data, "service", "serviceImpl", "serviceImpl", "java");
		
		//3.5 参数-bean.java文件
		putData(data, "model", "model", "", "java");
				
		//3.6 参数-mapper.java文件
		putData(data, "dao", "dao", "mapper", "java");
		
		//4.1 controller.java
		if(controller){			
			processFile.templateProcess(data , "controller.java.ftl", data.get("controllerOutputPath").toString());
		}
		
		//4.2 service
		if(service){				
			//service.java
			processFile.templateProcess(data , "service.java.ftl", data.get("serviceOutputPath").toString());
			//serviceImpl.java
			processFile.templateProcess(data , "serviceImpl.java.ftl", data.get("serviceImplOutputPath").toString());
		}
		
		//4.3 bean.java
		if(javaBean){				
			processFile.templateProcess(data , "javabean.java.ftl", data.get("modelOutputPath").toString());
		}
		
		//4.4 mapper.java
		if(mapperDao){				
			processFile.templateProcess(data , "mapper.java.ftl", data.get("daoOutputPath").toString());
		}
		
		//4.5 mapper.xml
		if(mapperXml){				
			String mapperXmlOutputPath = processFile.getResourcesPath()+File.separator+"mapper"+File.separator+model+File.separator+data.get("daoClassName").toString()+".xml";
			processFile.templateProcess(data , "mapper.xml.ftl", mapperXmlOutputPath);
		}
		
		//4.6 index.ftl
		if(indexftl){			
			String indexFtlOutputPath = processFile.getWebappPath()+File.separator+"WEB-INF"+File.separator+"templates"+File.separator+model+File.separator+ftpmodel+"index.ftl";
			processFile.templateProcess(data , "index.ftl.ftl", indexFtlOutputPath);
		}
		
		//4.7 oper.ftl
		if(operftl){				
			String operFtlOutputPath = processFile.getWebappPath()+File.separator+"WEB-INF"+File.separator+"templates"+File.separator+model+File.separator+ftpmodel+"oper.ftl";
			processFile.templateProcess(data , "oper.ftl.ftl", operFtlOutputPath);
		}
	}
}
