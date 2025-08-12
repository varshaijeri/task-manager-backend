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

        // Skip for local development
        if (baseUrl.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        // Force HTTPS redirect if X-Forwarded-Proto is http
        String proto = httpRequest.getHeader("x-forwarded-proto");
        if (proto != null && !proto.equalsIgnoreCase("https")) {
            String httpsUrl = baseUrl + httpRequest.getRequestURI();
            httpResponse.sendRedirect(httpsUrl);
            return;
        }


        // Wrap request to always say "https" if behind Fly.io
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public String getScheme() {
                String proto = httpRequest.getHeader("x-forwarded-proto");
                return proto != null ? proto : super.getScheme();
            }

            @Override
            public boolean isSecure() {
                return "https".equalsIgnoreCase(getScheme()) || super.isSecure();
            }

            @Override
            public StringBuffer getRequestURL() {
                StringBuffer url = super.getRequestURL();
                if (proto != null && !url.toString().startsWith(baseUrl)) {
                    return new StringBuffer(baseUrl + httpRequest.getRequestURI());
                }
                return url;
            }
        };

        chain.doFilter(wrappedRequest, response);
    }
}
