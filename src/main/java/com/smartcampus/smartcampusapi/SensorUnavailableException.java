package com.smartcampus.smartcampusapi;

/**
 * Adapted from Tutorial Week 09 (DataNotFoundException.java)
 * Thrown when a POST reading is attempted on a sensor with MAINTENANCE status.
 * Mapped to HTTP 403 Forbidden by SensorUnavailableExceptionMapper.
 * Extends RuntimeException so it does not need to be declared
 * in method signatures, keeping resource code clean.
 */

public class SensorUnavailableException extends RuntimeException {

    /**
     * Constructs a new SensorUnavailableException with a descriptive message
     * identifying which sensor is unavailable and why.
     * @param message details of the issue e.g. "Sensor 'TEMP-001' is under maintenance"
     */
    
    public SensorUnavailableException(String message) {
        super(message);
    }
    
}