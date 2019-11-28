package com.example.common.config.swagger;

import com.example.common.config.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
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

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/11/28 14:38
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createSwagger() {

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("Bearer")
            .description("身份token")
            .modelRef(new ModelRef("String"))
            .required(true)
            .parameterType("header")
            .required(false).build();
        parameters.add(parameterBuilder.build());


        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("测试Api")
            .description("test-api")
            .contact(new Contact("测试公司", "www.baidu.com", "123456@qq.com"))
            .version("1.0")
            .build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .globalOperationParameters(parameters)
            .apiInfo(apiInfo)
            .enable(true) //生产环境下关闭
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example"))
            .paths(PathSelectors.any())
            .build();
        return docket;
    }
}
