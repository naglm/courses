package com.example.courses.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class CoursesConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * Group User contains operations related to user management such as login/logout
     */
/*    @Bean
    public Docket swaggerUserApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("User")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.courses.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(apiKey()));
    }*/

    /*private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Courses application - REST APIs")
                .description("Spring Boot & Angular showcase application").termsOfServiceUrl("")
                .contact(new Contact("Martin Nagl", "https://github.com/mnagl", ""))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("0.0.1")
                .build();
    }*/

    /*private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    } */
}
