package com.varsha.taskmanager.Customizer;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class GlobalAuthorizationHeaderCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // Skip for login endpoint
        if (!handlerMethod.getMethod().getName().equals("login")) {
            // Ensure security requirements exist
            if (operation.getSecurity() == null || operation.getSecurity().isEmpty()) {
                operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
            }
        }
        return operation;
    }
}