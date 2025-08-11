package com.varsha.taskmanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://taskmanager-hidden-sky-719.fly.dev/").description("Production Server")
//                        new Server().url("/").description("Localhost")
                ))
                .info(new Info()
                        .title("Task Manager API")
                        .version("1.0")
                        .description("API documentation with JWT authentication"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation")
                        .url("https://taskmanager-hidden-sky-719.fly.dev/swagger-ui.html"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}