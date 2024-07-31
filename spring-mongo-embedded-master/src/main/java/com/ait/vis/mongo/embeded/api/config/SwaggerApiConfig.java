package com.ait.vis.mongo.embeded.api.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerApiConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("MongoDB-Relationships").select()
				.apis(RequestHandlerSelectors.basePackage("com.ait.vis.mongo.embeded.api")).paths(PathSelectors.any())
				.build().apiInfo(apiInfoMetaData());
	}

	private ApiInfo apiInfoMetaData() {

		return new ApiInfoBuilder().title("API Documentation")
				.description("Rest API for MongoDB one-to-one and one-to-many relationships")
				.contact(new Contact("Dev-Team", "http://localhost:2024/swagger-ui/index.html", "alokebd@gmail.com"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("2.0.0")
				.build();
	}

}
