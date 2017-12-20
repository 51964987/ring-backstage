package ringbackstage.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
public class SessionFactoryConfig implements TransactionManagementConfigurer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/** * mybatis 配置路径 */
	private static String MYBATIS_CONFIG = "mybatis-config.xml";
	/** * mybatis 配置路径 */
	private String typeAliasPackage = "ringbackstage.web.model";

	@Autowired
	private DataSource dataSource;


	/**
	 * 创建sqlSessionFactoryBean 实例 并且设置configtion 如驼峰命名.等等 设置mapper 映射路径
	 * 设置datasource数据源
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory createSqlSessionFactoryBean() throws Exception {
		try {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			/** 设置mybatis configuration 扫描路径 */
			sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
			/** 设置datasource */
			sqlSessionFactoryBean.setDataSource(dataSource);
			/** 设置typeAlias 包扫描路径 */
			sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
			
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));
			return sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}