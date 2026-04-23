package com.smartcampus.smartcampusapi.exception;

import com.smartcampus.smartcampusapi.exception.RoomNotEmptyException;
import com.smartcampus.smartcampusapi.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Adapted from Tutorial Week 09 (DataNotFoundExceptionMapper.java)
 * Intercepts RoomNotEmptyException and returns a structured 409 Conflict response.
 * Registered automatically with the JAX-RS runtime via the @Provider annotation.
 * Triggered when a DELETE room request is blocked due to active sensors still assigned.
 */
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    /**
     * Translates a RoomNotEmptyException into an HTTP 409 Conflict response.
     * 409 Conflict is used because the request is valid but conflicts with
     * the current state of the resource — sensors are still assigned to the room.
     */
    
    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        // Build a standardised error payload using the ErrorMessage model
        ErrorMessage errorMessage = new ErrorMessage(
            exception.getMessage(),
            409,
            "https://smartcampus.ac.uk/api/docs/errors"
        );

        return Response.status(Status.CONFLICT)
                .entity(errorMessage)
                .type("application/json")
                .build();
    }
}