package com.swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Value("${swagger.group:bigtreefinance}")
	private String group;
	@Value("${swagger.title:未设置项目标题}")
	private String title;
	@Value("${swagger.description:未设置项目描述}")
	private String description;
	@Value("${swagger.version:未设置版本号}")
	private String version;
	@Value("${swagger.termsOfServiceUrl:未设置服务地址}")
	private String termsOfServiceUrl;
	@Value("${swagger.contact:未设置联系人}")
	private String contact;
	// 可以取server.servlet.context-path
	@Value("${swagger.basepath:/}")
	private String basepath;
	@Value("${swagger.basepackage:com}")
	private String basepackage;
	@Value("${swagger.show:true}")
	private Boolean show;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(show)
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(false)
				.pathMapping(basepath)
				.select()
				.apis(RequestHandlerSelectors.basePackage(basepackage))
				.build()
				.apiInfo(productApiInfo());
	}
	
	private ApiInfo productApiInfo() {
		ApiInfo apiInfo = new ApiInfo(title, description, version, termsOfServiceUrl, contact, "LICENSE-2.0", "http://www.apache.org/licenses/LICENSE-2.0");
		return apiInfo;
	}
}