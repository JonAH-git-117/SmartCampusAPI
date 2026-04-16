package com.smartcampus.smartcampusapi;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Adapted from Tutorial Week 09 (DataNotFoundExceptionMapper.java)
 * Intercepts SensorUnavailableException and returns a structured 403 Forbidden response.
 * Registered automatically with the JAX-RS runtime via the @Provider annotation.
 * Triggered when a POST reading is attempted on a sensor in MAINTENANCE status.
 */

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    /**
     * Translates a SensorUnavailableException into an HTTP 403 Forbidden response.
     * 403 Forbidden is used because the client is not permitted to post readings
     * to a sensor that is currently under maintenance.
     */
    
    @Override
    public Response toResponse(SensorUnavailableException exception) {
        
        // Build a standardised error payload using the ErrorMessage model
        ErrorMessage errorMessage = new ErrorMessage(
            exception.getMessage(),
            403,
            "https://smartcampus.ac.uk/api/docs/errors"
        );

        return Response.status(Status.FORBIDDEN)
                .entity(errorMessage)
                .type("application/json")
                .build();
    }
    
}