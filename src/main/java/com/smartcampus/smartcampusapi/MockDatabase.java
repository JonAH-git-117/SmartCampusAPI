package com.smartcampus.smartcampusapi;

import java.util.ArrayList;
import java.util.List;
/**
 * MockDatabase
 * Adapted directly from Week 8 tutorial (MockDatabase.java)
 * 
 * Replaces the tutorial's Teachers/Students/Modules with
 * Rooms, Sensors and SensorReadings as required by the spec.
 * 
 * Uses static lists to simulate an in-memory database.
 * No actual database is used as per the spec requirements.
 */
public class MockDatabase {

    // In-memory list of all Rooms (replaces tutorial's TEACHERS list)
    public static final List<Room> ROOMS = new ArrayList<>();

    // In-memory list of all Sensors (replaces tutorial's STUDENTS list)
    public static final List<Sensor> SENSORS = new ArrayList<>();

    // In-memory list of all SensorReadings (replaces tutorial's MODULES list)   
    public static final List<SensorReading> READINGS = new ArrayList<>();

    // Static initialiser block - pre-populates the lists with sample data
    // Copied from tutorial's static block pattern
    static {
        // Sample Rooms
        ROOMS.add(new Room("LIB-301", "Library Quiet Study", 50));
        ROOMS.add(new Room("LAB-101", "Computer Lab", 30));

        // Sample Sensors
        SENSORS.add(new Sensor("TEMP-001", "Temperature", "ACTIVE", 22.5, "LIB-301"));
        SENSORS.add(new Sensor("CO2-001", "CO2", "ACTIVE", 400.0, "LAB-101"));

        // Link sensors to their rooms
        ROOMS.get(0).getSensorIds().add("TEMP-001");
        ROOMS.get(1).getSensorIds().add("CO2-001");
    }
}