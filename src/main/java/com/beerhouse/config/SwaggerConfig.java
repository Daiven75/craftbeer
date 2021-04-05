package com.beerhouse.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private final ResponseMessage m201 = customMessage201();
	private final ResponseMessage m200 = simpleMessage(200, "OK!");
	private final ResponseMessage m204del = simpleMessage(204, "Deletion OK!");
	private final ResponseMessage m500 = simpleMessage(500, "unexpected error");
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				
				// customizando possiveis retornos e mensagens dos metodos HTTP
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m200, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m200, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m500))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.beerhouse.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API craft-beer",
				"",
				"Versão 1.0",
				"",
				new Contact("", 
						"https://github.com/Daiven75/craftbeer", 
						""),
				"",
				"", 
				Collections.emptyList()
		);
	}
	
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}
	
	private ResponseMessage customMessage201() {
		Map<String, Header> headers = new HashMap<>();
		headers.put("location", new Header("location", "URI new Resource created", new ModelRef("string")));
		
		return new ResponseMessageBuilder()
				.code(201)
				.message("Resource created")
				.headersWithDescription(headers)
				.build();
	}
}