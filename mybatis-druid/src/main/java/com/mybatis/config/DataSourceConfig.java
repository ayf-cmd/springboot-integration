package com.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.StringTokenizer;

@Slf4j
@Configuration
@MapperScan(basePackages = { DataSourceConfig.BASE_PACKAGE, DataSourceConfig.BASE_PACKAGE_CUSTOMIZE }, sqlSessionFactoryRef = DataSourceConfig.SQL_SESSION_FACTORY)
public class DataSourceConfig {
	@Autowired
	private DruidDataSourceConfig druidDataSourceConfig;
	/***
	 * 数据源bean的名称
	 */
	private final static String DATASOURCE = "dataSource";
	final static String SQL_SESSION_FACTORY = "sqlSessionFactory";
	/**
	 * mapper注解扫描包路径
	 */
	final static String BASE_PACKAGE = "com.fintell.dp3.db";
	final static String BASE_PACKAGE_CUSTOMIZE = "com.fintell.dp3.db.customize.mapper";
	/**
	 * 初始化表情符号
	 **/
	private final static String INIT_SQL_UTF8MB4 = "SET NAMES utf8mb4;";

	@Bean(name = DATASOURCE, initMethod = "init", destroyMethod = "close")
	@Primary
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(druidDataSourceConfig.driverClassName);
		dataSource.setUrl(druidDataSourceConfig.url);
		dataSource.setUsername(druidDataSourceConfig.username);
		dataSource.setPassword(druidDataSourceConfig.password);
		try {
			dataSource.setFilters(druidDataSourceConfig.filters);
		} catch (SQLException e) {
			log.error("error : ", e);
		}
		dataSource.setMaxActive(druidDataSourceConfig.maxActive);
		dataSource.setInitialSize(druidDataSourceConfig.initialSize);
		dataSource.setMaxWait(druidDataSourceConfig.maxWait);
		dataSource.setMinIdle(druidDataSourceConfig.minIdle);
		dataSource.setTimeBetweenConnectErrorMillis(druidDataSourceConfig.timeBetweenEvictionRunsMillis);
		dataSource.setMinEvictableIdleTimeMillis(druidDataSourceConfig.minEvictableIdleTimeMillis);
		// 申请连接的时候检测，如果空闲时间大于
		// timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。建议配置为true，不影响性能，并且保证安全性。
		dataSource.setTestWhileIdle(druidDataSourceConfig.testWhileIdle);
		dataSource.setValidationQuery(druidDataSourceConfig.validationQuery);
		dataSource.setTestOnBorrow(druidDataSourceConfig.testOnBorrow);
		dataSource.setTestOnReturn(druidDataSourceConfig.testOnReturn);
		dataSource.setPoolPreparedStatements(druidDataSourceConfig.poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidDataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
		dataSource.setMaxOpenPreparedStatements(druidDataSourceConfig.maxOpenPreparedStatements);
		// 设置支持表情符号
		dataSource.setConnectionInitSqls(Collections.list(new StringTokenizer(INIT_SQL_UTF8MB4, ";")));
		//慢sql
		dataSource.setConnectionProperties(druidDataSourceConfig.getConnectionProperties());
		dataSource.setUseGlobalDataSourceStat(druidDataSourceConfig.isUseGlobalDataSourceStat());
		dataSource.setUseLocalSessionState(false);

		return dataSource;
	}

	@Bean
//	@Profile({ "localdev", "remotetest" })
	public ServletRegistrationBean<StatViewServlet>  druidServlet() {
		log.info("********************************************************");
		log.info("加载druid servlet");
		log.info("********************************************************");
		//创建servlet注册实体
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		//设置ip白名单
		servletRegistrationBean.addInitParameter("allow","127.0.0.1");
		//设置ip黑名单
		servletRegistrationBean.addInitParameter("deny", druidDataSourceConfig.getDruidBlackList());
		servletRegistrationBean.addInitParameter("sessionStatEnable","false");
		//设置控制台管理用户__登录用户名和密码
		servletRegistrationBean.addInitParameter("loginUsername", druidDataSourceConfig.getDruidUserName());
		servletRegistrationBean.addInitParameter("loginPassword", druidDataSourceConfig.getDruidPwd());
		//是否可以重置数据
		servletRegistrationBean.addInitParameter("resetEnable","false");
		return servletRegistrationBean;
	}

	@Bean
	//	@Profile({ "localdev", "remotetest" })
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*,/druid/*，" +
				"/swagger-resources/*,/loginTest/*,/api/*,/webjars/*./webSocketServer/*,/webSocketTest/*,*.html,*.json");
		return filterRegistrationBean;
	}

	@Bean(name = SQL_SESSION_FACTORY)
	@Primary
	public SqlSessionFactory flowSqlSessionFactory(@Qualifier(DATASOURCE) DataSource flowDataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(flowDataSource);
 		// 配置config
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		// 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。
		configuration.setMapUnderscoreToCamelCase(true);
		sessionFactory.setConfiguration(configuration);
		return sessionFactory.getObject();
	}

}
