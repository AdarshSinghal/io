package com.adarsh.io;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for Swagger customization
 * 
 * @author adarshsinghal
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

	private static final String DESCRIPTION = "Includes APIs";

	private static final String API_INFO = "REST API for GNF User & Group DataService";
	//private static final String WEBJARS_PATH = "/webjars/**";
	private static final String WEBJARS_PATH = "/**";

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.adarsh.io.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo()).globalOperationParameters(Arrays.asList());

	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler(WEBJARS_PATH).addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	private ApiInfo apiInfo() {

		return new ApiInfo(API_INFO, DESCRIPTION, null, null, null, null, null, Collections.emptyList());
	}

}
