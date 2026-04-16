package com.smartcampus.smartcampusapi;

/**
 * Adapted from Tutorial Week 08 (BaseModel.java)
 * Defines the contract that all model classes must implement to be
 * compatible with GenericDAO. By requiring getId() and setId(),
 * GenericDAO can perform lookups and updates on any model type
 * without needing to know the specific class.
 * Modified from the tutorial version:
 * - ID type changed from int to String to support identifiers
 *   like "LIB-301", "TEMP-001" as required by the spec.
 * Implemented by: Room, Sensor, SensorReading
 */

public interface BaseModel {

    /**
     * Returns the unique String identifier for this model instance.
     */
    
    String getId();

    /**
     * Sets the unique String identifier for this model instance.
     * @param id the unique identifier e.g. "LIB-301"
     */
    
    void setId(String id);
    
}