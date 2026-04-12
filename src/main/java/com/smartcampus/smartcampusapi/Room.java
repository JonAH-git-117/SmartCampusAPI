package com.smartcampus.smartcampusapi;

import java.util.ArrayList;
import java.util.List;
/**
 * Room Model
 * Copied from the spec's Room POJO definition.
 * Structured similarly to the Week 8 tutorial's Student.java model.
 * Implements BaseModel (adapted to use String IDs) for use with GenericDAO.
 */
public class Room implements BaseModel {

    // Unique identifier e.g. "LIB-301"
    private String id;

    // Human-readable name e.g. "Library Quiet Study"
    private String name;

    // Maximum occupancy for safety regulations
    private int capacity;

    // Collection of IDs of sensors deployed in this room
    private List<String> sensorIds = new ArrayList<>();

    // --- Constructors ---
    public Room() {
    this.sensorIds = new ArrayList<>();
    }

    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    // --- BaseModel implementation ---
    @Override
    public String getId() { return id; }

    @Override
    public void setId(String id) { this.id = id; }

    // --- Getters and Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<String> getSensorIds() { return sensorIds; }
    public void setSensorIds(List<String> sensorIds) { this.sensorIds = sensorIds; }
}