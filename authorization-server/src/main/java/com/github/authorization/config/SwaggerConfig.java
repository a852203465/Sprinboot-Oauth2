package com.github.authorization.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 *  swagger 配置
 * @author Rong.Jia
 * @date 2019/01/07 15:36:22
 */
@Configuration
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private Boolean enable;

    /**
     * @desritipn: 初始化
     * @param groupName   组名
     * @param basePackage 扫描路径
     * @date 2019/01/07 15:46:33
     * @author Rong.Jia
     * @return Docket
     */
    private Docket initDocket(String groupName, String basePackage) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("监控基础平台")
                .description("监控基础平台接口文档说明")
                .termsOfServiceUrl("localhost:9093")
                .contact(new Contact("monitoring_basePlatform", "", "rong.jia@unionman.com.cn"))
                .version("1.0")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo()).
                useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {

        List<ApiKey> list = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        list.add(apiKey);

        return list;
    }

    private List<SecurityContext> securityContexts() {

        List<SecurityContext> list = new ArrayList<>();
        SecurityContext build = SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("^(?!auth).*$")).build();
        list.add(build);

        return list;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> list = new ArrayList<>();
        SecurityReference authorization = new SecurityReference("Authorization", authorizationScopes);
        list.add(authorization);

        return list;
    }
}
