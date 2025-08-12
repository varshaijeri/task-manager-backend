package com.varsha.taskmanager.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpsEnforcerFilter implements Filter {

    @Value("${app.base-url:}")
    private String baseUrl;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String proto = httpRequest.getHeader("x-forwarded-proto");

        // Skip for Swagger endpoints to avoid redirect loops
        if (httpRequest.getRequestURI().startsWith("/swagger-ui") ||
                httpRequest.getRequestURI().startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        // Force HTTPS redirect in production
        if (!baseUrl.isEmpty() && proto != null && !proto.equalsIgnoreCase("https")) {
            String httpsUrl = baseUrl + httpRequest.getRequestURI();
            httpResponse.sendRedirect(httpsUrl);
            return;
        }

        chain.doFilter(request, response);

    }
}
