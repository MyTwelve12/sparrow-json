package com.sparrowjson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi(){
		ParameterBuilder tokenPar=new ParameterBuilder();
		tokenPar.name("Cookie").description("Cookie")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false)
				.build();
		List<Parameter> pars = new ArrayList<Parameter>();
		pars.add(tokenPar.build());

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
                .apis(RequestHandlerSelectors.basePackage("com.sparrowjson.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
	}
	
	private ApiInfo apiInfo(){
		return new ApiInfo("Spring Boot中使用Swagger2构建RESTful APIs",
			      "更多Spring Boot相关文章请关注：http://blog.didispace.com/",
			      "1.0",
			      "http://blog.didispace.com/",
			      new Contact("极策", "http://exmind.com", "tospur-api@exmind.com"),
			      null,
			      null);
	}

}
