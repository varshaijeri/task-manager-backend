package com.varsha.taskmanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${app.base-url:}")
    private String baseUrl;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    private boolean isProduction() {
        return activeProfile.contains("prod") ||
                !baseUrl.isEmpty() && baseUrl.startsWith("https");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .version("1.0")
                        .description("API documentation with JWT authentication"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation")
                        .url(isProduction() ?
                                baseUrl + "/swagger-ui.html" :
                                "http://localhost:8080/swagger-ui.html"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));

        // Always add both servers to handle all cases
        openAPI.addServersItem(new Server()
                .url(baseUrl)
                .description("Production Server"));
        openAPI.addServersItem(new Server()
                .url("http://localhost:8080")
                .description("Development Server"));

        return openAPI;
    }
}