package com.varsha.taskmanager.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HttpsEnforcerFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        logger.debug("Incoming Request: {} {} | Headers: {}",
                request.getMethod(),
                request.getRequestURI(),
                Collections.list(request.getHeaderNames()).stream()
                        .collect(Collectors.toMap(h -> h, request::getHeader)));
        chain.doFilter(request, response);
    }
}