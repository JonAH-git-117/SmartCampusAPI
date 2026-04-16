package com.smartcampus.smartcampusapi;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * LinkedResourceNotFoundExceptionMapper
 * Adapted from Tutorial Week 09 (DataNotFoundExceptionMapper.java)
 * Intercepts LinkedResourceNotFoundException and returns a structured 422 response.
 */
@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
            exception.getMessage(),
            422,
            "https://smartcampus.ac.uk/api/docs/errors"
        );

        return Response.status(422)
                .entity(errorMessage)
                .type("application/json")
                .build();
    }
}