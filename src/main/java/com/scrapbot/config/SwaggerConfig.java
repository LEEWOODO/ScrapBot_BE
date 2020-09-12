package com.scrapbot.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.scrapbot.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.enable(true);
				//.useDefaultResponseMessages(false)
				//.globalResponseMessage(RequestMethod.GET, getArrayList());
	
		return docket;

	}

	private ArrayList<ResponseMessage> getArrayList() {
		ArrayList<ResponseMessage> lists = new ArrayList<ResponseMessage>();
		lists.add(new ResponseMessageBuilder().code(500).message("서버에러").responseModel(new ModelRef("Error")).build());
		lists.add(new ResponseMessageBuilder().code(403).message("권한없음").responseModel(new ModelRef("Forbbiden"))
				.build());
		return lists;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("API 타이틀").description("API 상세소개 및 사용법 등").version("1.0").build();

	}

}
