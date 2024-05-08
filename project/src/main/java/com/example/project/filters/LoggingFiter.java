package org.zerogravitysolutions.digitalschool.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Profile("default")
public class LoggingFiter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFiter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // log incoming request
        logger.info("Incoming request {} {}", request.getMethod(), request.getRequestURI());

        // continue processing the request
        filterChain.doFilter(request, response);

        // log outgoing response
        logger.info("Outgoing response {} for {} {}", response.getStatus(), request.getMethod(), request.getRequestURI());

        // set error status to 500 if the response status is > 500
        if(response.getStatus() >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
