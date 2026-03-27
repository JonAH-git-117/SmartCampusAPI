package com.smartcampus.smartcampusapi;
/**
 * SensorReading Model
 * Copied from the spec's SensorReading POJO definition.
 * Represents a single historical reading captured by a sensor.
 * Implements BaseModel (adapted to use String IDs) for use with GenericDAO.
 */
public class SensorReading implements BaseModel {

    // Unique reading event ID (UUID recommended by spec)
    private String id;

    // Epoch time (ms) when the reading was captured
    private long timestamp;

    // The actual metric value recorded by the hardware
    private double value;

    // Links this reading back to its parent sensor
    private String sensorId;

    // --- Constructors ---
    public SensorReading() {}

    public SensorReading(String id, long timestamp, double value, String sensorId) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.sensorId = sensorId;
    }

    // --- BaseModel implementation ---
    @Override
    public String getId() { return id; }

    @Override
    public void setId(String id) { this.id = id; }

    // --- Getters and Setters ---
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
}