package com.smartcampus.smartcampusapi.model;

/**
 * Copied from the spec's Sensor POJO definition (Smart Campus Coursework Spec, Part 1).
 * Structured similarly to Tutorial Week 08's Module.java model.
 * Implements BaseModel to support use with GenericDAO (String ID version).
 * Represents a physical sensor deployed in a campus room.
 * The roomId field acts as a foreign key linking this sensor to its parent Room.
 * The status field controls business logic — sensors in MAINTENANCE cannot
 * accept new readings (enforced by SensorUnavailableException in Part 5).
 */

public class Sensor implements BaseModel {

    // Unique identifier e.g. "TEMP-001" — provided by the client, not auto-generated
    private String id;

    // Sensor category e.g. "Temperature", "Occupancy", "CO2"
    private String type;

    // Current operational state — must be "ACTIVE", "MAINTENANCE", or "OFFLINE"
    private String status;

    // The most recent measurement recorded by this sensor
    // Updated automatically as a side effect when a new reading is posted
    private double currentValue;

    // Foreign key linking this sensor to its parent Room
    private String roomId;

    // Constructors 
    /**
     * Default constructor required by Jackson for JSON deserialisation.
     */
    
    public Sensor() {}

    /**
     * Convenience constructor for pre-populating MockDatabase with sample data.
     * @param id unique sensor identifier e.g. "TEMP-001"
     * @param type sensor category e.g. "Temperature"
     * @param status operational state e.g. "ACTIVE"
     * @param currentValue most recent measurement
     * @param roomId ID of the room this sensor is deployed in
     */
    
    public Sensor(String id, String type, String status, double currentValue, String roomId) {
        
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
        
    }

    // BaseModel implementation
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    
}