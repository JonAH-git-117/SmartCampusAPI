package com.smartcampus.smartcampusapi;

import java.util.ArrayList;
import java.util.List;

/**
 * Copied from the spec's Room POJO definition (Smart Campus Coursework Spec, Part 1).
 * Structured similarly to Tutorial Week 08's Student.java model.
 * Implements BaseModel to support use with GenericDAO (String ID version).
 * Represents a physical room on campus that can contain multiple sensors.
 * The sensorIds list maintains referential integrity with the Sensor collection.
 */

public class Room implements BaseModel {

    // Unique identifier e.g. "LIB-301" — provided by the client, not auto-generated
    private String id;

    // Human-readable name e.g. "Library Quiet Study"
    private String name;

    // Maximum occupancy for safety regulations
    private int capacity;

    // IDs of sensors currently deployed in this room
    // Initialised as an empty list to prevent NullPointerException on deserialisation
    private List<String> sensorIds = new ArrayList<>();

    // Constructors 

    /**
     * Default constructor required by Jackson for JSON deserialisation.
     * Explicitly initialises sensorIds to prevent null list on POST requests.
     */
    
    public Room() {
        
        this.sensorIds = new ArrayList<>();
        
    }

    /**
     * Convenience constructor for pre-populating MockDatabase with sample data.
     * @param id unique room identifier e.g. "LIB-301"
     * @param name human-readable room name
     * @param capacity maximum occupancy
     */
    
    public Room(String id, String name, int capacity) {
        
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        
    }

    // BaseModel implementation
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<String> getSensorIds() { return sensorIds; }
    public void setSensorIds(List<String> sensorIds) { this.sensorIds = sensorIds; }
    
}