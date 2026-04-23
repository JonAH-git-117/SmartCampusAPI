package com.smartcampus.smartcampusapi.exception;

import com.smartcampus.smartcampusapi.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

/**
 * Adapted from Lecture Week 08 (GlobalExceptionMapper slide)
 * Acts as a catch-all safety net for any unexpected runtime exceptions
 * such as NullPointerException or IndexOutOfBoundsException.
 * Ensures internal stack traces are never exposed to the client,
 * returning a clean 500 response instead.
 */

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    // Logger used to record the real error internally for debugging
    // The actual exception details are never sent to the client
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    /**
     * Intercepts any Throwable not caught by a more specific mapper.
     * JAX-RS uses a closest-match algorithm so specific mappers
     * (e.g. RoomNotEmptyExceptionMapper) take priority over this one.
     * @param exception the unexpected exception that was thrown
     */
    
    @Override
    public Response toResponse(Throwable exception) {
        
        // Log the real error internally for debugging by system administrators
        LOGGER.severe("Unexpected error: " + exception.getMessage());

        // Return a safe generic message to the client
        // Never expose internal class names, line numbers or stack traces
        ErrorMessage errorMessage = new ErrorMessage(
            "An unexpected internal server error occurred.",
            500,
            "https://smartcampus.ac.uk/api/docs/errors"
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .type("application/json")
                .build();
    }
    
}