package com.varsha.taskmanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public Map<String, String> healthCheck(HttpServletRequest request) {
        return Map.of(
                "scheme", request.getScheme(),
                "x-forwarded-proto", request.getHeader("x-forwarded-proto"),
                "isSecure", String.valueOf(request.isSecure()),
                "serverName", request.getServerName()
        );
    }
}