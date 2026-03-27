package com.smartcampus.smartcampusapi;
/**
 * BaseModel Interface
 * Copied directly from Week 8 tutorial (BaseModel.java)
 * Then adapted to use String id rather than int id as per the spec
 * 
 * Every model class (Room, Sensor, SensorReading) must implement this.
 * This is what allows GenericDAO to call getId() and setId() on any model.
 */
public interface BaseModel {
    
    String getId();
    void setId(String id);
    
}