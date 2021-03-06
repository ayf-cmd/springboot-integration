package com.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.StringTokenizer;

@Slf4j
@Configuration
// 配置扫描包
@MapperScan(basePackages = {DuridDataSourceFactory.BASE_PACKAGE}, sqlSessionFactoryRef = DuridDataSourceFactory.SQL_SESSION_FACTORY)
public class DuridDataSourceFactory {
    /**
     * 配置文件
     */
    @Autowired
    private DruidDataSourceConfig druidDataSourceConfig;
    @Autowired
    private MybatisProperties mybatisProperties;
    /***
     * 数据源bean的名称
     */
    private final static String DATASOURCE = "dataSource";
    /**
     * mapper注解扫描包路径
     */
    final static String BASE_PACKAGE = "com.mybatis.mapper";
    /**
     * 初始化表情符号
     **/
    private final static String INIT_SQL_UTF8MB4 = "SET NAMES utf8mb4;";
    /**
     * SQL_SESSION_FACTORY
     */
    final static String SQL_SESSION_FACTORY = "sqlSessionFactory";
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    /**
     * 配置数据源
     */
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
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
        log.info("********************************************************");
        log.info("加载druid servlet");
        log.info("********************************************************");
        //创建servlet注册实体
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //设置ip白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //设置ip黑名单
        servletRegistrationBean.addInitParameter("deny", druidDataSourceConfig.getDruidBlackList());
        servletRegistrationBean.addInitParameter("sessionStatEnable", "false");
        //设置控制台管理用户__登录用户名和密码
        servletRegistrationBean.addInitParameter("loginUsername", druidDataSourceConfig.getDruidUserName());
        servletRegistrationBean.addInitParameter("loginPassword", druidDataSourceConfig.getDruidPwd());
        //是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*,/druid/*，" +
                "/swagger-resources/*,/loginTest/*,/api/*,/webjars/*./webSocketServer/*,/webSocketTest/*,*.html,*.json");
        return filterRegistrationBean;
    }

    @Bean(name = SQL_SESSION_FACTORY)
    @Primary
    public SqlSessionFactory flowSqlSessionFactory(@Qualifier(DATASOURCE) DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        // 配置mapperLocations (如果这里配置了，则properties里面配置则会失效)
        // 解决方案 : 其它属性同理
        // 1. 重新配置 (properties无需添加 mybatis.mapper-locations=classpath:mappers/*.xml )
        sessionFactory.setMapperLocations(resourceResolver.getResources("classpath:mappers/*.xml"));
        // 2. properties添加 mybatis.mapper-locations=classpath:mappers/*.xml ， 从mybatisproperties获取
//        if (!ObjectUtils.isEmpty(mybatisProperties.getMapperLocations())) {
//            sessionFactory.setMapperLocations(mybatisProperties.resolveMapperLocations());
//        }
        // 配置 typeAliasesPackage
        sessionFactory.setTypeAliasesPackage("com.mybatis.entity");

        // 配置数据源
        sessionFactory.setDataSource(dataSource);

        // 配置mybatis.configuration信息
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogPrefix(BASE_PACKAGE);
//        configuration.setLogImpl(StdOutImpl.class);

        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }

}
