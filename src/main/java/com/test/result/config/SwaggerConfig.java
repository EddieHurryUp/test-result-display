package com.test.result.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 * 
 * @author qijiaxi
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("测试结果展示系统 API")
                .description("测试结果展示系统的RESTful API文档")
                .version("1.0.0")
                .contact(new Contact()
                    .name("测试团队")
                    .email("test@example.com")
                    .url("http://localhost:8080"))
                .termsOfService("http://localhost:8080/terms")
            );
    }
}