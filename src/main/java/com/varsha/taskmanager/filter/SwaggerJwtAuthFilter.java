package com.varsha.taskmanager.filter;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class SwaggerJwtAuthFilter implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // Add security requirement for all operations except login
        if (!handlerMethod.getMethod().getName().equals("login")) {
            operation.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
                    .addList("bearerAuth"));
        }

        // Add Authorization header parameter
        Parameter authorizationHeader = new Parameter()
                .in("header")
                .name("Authorization")
                .description("JWT token")
                .required(true)
                .schema(new io.swagger.v3.oas.models.media.StringSchema()
                        .example("Bearer your.jwt.token.here"));

        operation.addParametersItem(authorizationHeader);

        return operation;
    }
}