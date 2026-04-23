package com.smartcampus.smartcampusapi.exception;

/**
 * LinkedResourceNotFoundException
 * Adapted from Tutorial Week 09 (DataNotFoundException.java)
 * Thrown when a POST sensor references a roomId that does not exist.
 * Mapped to HTTP 422 Unprocessable Entity by LinkedResourceNotFoundExceptionMapper.
 */
public class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String message) {
        super(message);
    }
}