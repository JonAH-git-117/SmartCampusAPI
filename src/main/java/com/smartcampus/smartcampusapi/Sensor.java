package com.smartcampus.smartcampusapi;
/**
 * Sensor Model
 * Copied from the spec's Sensor POJO definition.
 * Structured similarly to the Week 8 tutorial's Module.java model.
 * Implements BaseModel (adapted to use String IDs) for use with GenericDAO.
 */
public class Sensor implements BaseModel {

    // Unique identifier e.g. "TEMP-001"
    private String id;

    // Category e.g. "Temperature", "Occupancy", "CO2"
    private String type;

    // Current state: "ACTIVE", "MAINTENANCE", or "OFFLINE"
    private String status;

    // The most recent measurement recorded
    private double currentValue;

    // Foreign key linking to the Room where the sensor is located
    private String roomId;

    // --- Constructors ---
    public Sensor() {}

    public Sensor(String id, String type, String status, double currentValue, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }

    // --- BaseModel implementation ---
    @Override
    public String getId() { return id; }

    @Override
    public void setId(String id) { this.id = id; }

    // --- Getters and Setters ---
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
}