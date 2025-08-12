package com.varsha.taskmanager.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpsEnforcerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Wrap request to always say "https" if behind Fly.io
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public String getScheme() {
                String proto = httpRequest.getHeader("x-forwarded-proto");
                if (proto != null) {
                    return proto;
                }
                return super.getScheme();
            }

            @Override
            public boolean isSecure() {
                return "https".equalsIgnoreCase(getScheme()) || super.isSecure();
            }
        };

        chain.doFilter(wrappedRequest, response);
    }
}
