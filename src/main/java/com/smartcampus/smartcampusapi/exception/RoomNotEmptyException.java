package com.smartcampus.smartcampusapi.exception;

/**
 * Adapted from Tutorial Week 09 (DataNotFoundException.java)
 * Thrown when a DELETE is attempted on a room that still has sensors assigned.
 * Mapped to HTTP 409 Conflict by RoomNotEmptyExceptionMapper.
 * Extends RuntimeException so it does not need to be declared
 * in method signatures, keeping resource code clean.
 */

public class RoomNotEmptyException extends RuntimeException {

    /**
     * Constructs a new RoomNotEmptyException with a descriptive message
     * identifying which room triggered the conflict.
     * @param message details of the conflict e.g. "Room 'LIB-301' still has sensors"
     */
    
    public RoomNotEmptyException(String message) {
        super(message);
    }
}