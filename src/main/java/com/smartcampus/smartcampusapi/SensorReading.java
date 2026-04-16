package com.smartcampus.smartcampusapi;

/**
 * Copied from the spec's SensorReading POJO definition (Smart Campus Coursework Spec, Part 1).
 * Represents a single historical reading captured by a sensor at a point in time.
 * Implements BaseModel to support use with GenericDAO (String ID version).
 * Each reading is linked to its parent sensor via sensorId.
 * IDs are auto-generated as UUIDs by SensorReadingResource if not provided by the client.
 * Timestamps are recorded as epoch milliseconds and auto-generated if not provided.
 */

public class SensorReading implements BaseModel {

    // Unique reading event ID — auto-generated as UUID if not provided by client
    private String id;

    // Epoch time in milliseconds when the reading was captured
    // Auto-generated using System.currentTimeMillis() if not provided by client
    private long timestamp;

    // The actual metric value recorded by the sensor hardware
    private double value;

    // Foreign key linking this reading back to its parent sensor
    private String sensorId;

    // Constructors 
    
    /**
     * Default constructor required by Jackson for JSON deserialisation.
     */
    
    public SensorReading() {}

    /**
     * Convenience constructor for creating readings programmatically.
     * @param id unique reading identifier (UUID recommended)
     * @param timestamp epoch milliseconds when the reading was captured
     * @param value the metric value recorded
     * @param sensorId ID of the sensor that produced this reading
     */
    
    public SensorReading(String id, long timestamp, double value, String sensorId) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.sensorId = sensorId;
    }

    // BaseModel implementation 
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    // Getters and Setters 
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    
}