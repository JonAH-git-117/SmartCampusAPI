package com.smartcampus.smartcampusapi;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Adapted directly from Tutorial Week 09 (LoggingFilter.java)
 * Implements both ContainerRequestFilter and ContainerResponseFilter
 * to log every incoming request and outgoing response automatically.
 * Using a filter for logging is preferable to manually adding Logger.info()
 * calls inside every resource method, as it centralises the logging concern
 * and keeps resource classes focused on business logic only.
 */

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    // Single shared logger instance for this filter class
    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());

    /**
     * Intercepts every incoming HTTP request before it reaches the resource method.
     * Logs the HTTP method and absolute URI for observability.
     * Adapted from Tutorial Week 09 LoggingFilter.java
     */
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("--- Incoming Request ---");
        LOGGER.info("Method: " + requestContext.getMethod());
        LOGGER.info("URI: " + requestContext.getUriInfo().getAbsolutePath());
    }

    /**
     * Intercepts every outgoing HTTP response after the resource method completes.
     * Logs the final HTTP status code regardless of whether the request succeeded or failed.
     * Adapted from Tutorial Week 09 LoggingFilter.java
     */
    
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        LOGGER.info("--- Outgoing Response ---");
        LOGGER.info("Status: " + responseContext.getStatus());
    }
    
}