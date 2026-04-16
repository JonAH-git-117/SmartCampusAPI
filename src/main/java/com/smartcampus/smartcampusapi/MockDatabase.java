package com.smartcampus.smartcampusapi;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapted from Tutorial Week 08 (MockDatabase.java)
 * Simulates an in-memory database using static ArrayList collections.
 * No actual database technology is used, as per the spec requirements.
 * Static lists are used so that data persists across requests for the
 * lifetime of the server session, regardless of JAX-RS per-request
 * resource class instantiation.
 * Modified from the tutorial version:
 * - Teachers/Students/Modules replaced with Rooms/Sensors/SensorReadings
 * - String IDs used throughout instead of auto-incremented integers
 * - SensorIds linked between Rooms and Sensors to maintain referential integrity
 */

public class MockDatabase {

    // In-memory list of all Rooms — replaces tutorial's TEACHERS list
    public static final List<Room> ROOMS = new ArrayList<>();

    // In-memory list of all Sensors — replaces tutorial's STUDENTS list
    public static final List<Sensor> SENSORS = new ArrayList<>();

    // In-memory list of all SensorReadings — replaces tutorial's MODULES list
    public static final List<SensorReading> READINGS = new ArrayList<>();

    /**
     * Static initialiser block — pre-populates the lists with sample data
     * so the API has data available immediately on startup.
     * Copied from tutorial's static block pattern.
     */
    
    static {
        
        // Sample Rooms
        ROOMS.add(new Room("LIB-301", "Library Quiet Study", 50));
        ROOMS.add(new Room("LAB-101", "Computer Lab", 30));

        // Sample Sensors
        SENSORS.add(new Sensor("TEMP-001", "Temperature", "ACTIVE", 22.5, "LIB-301"));
        SENSORS.add(new Sensor("CO2-001", "CO2", "ACTIVE", 400.0, "LAB-101"));

        // Link sensors to their rooms to maintain referential integrity
        ROOMS.get(0).getSensorIds().add("TEMP-001");
        ROOMS.get(1).getSensorIds().add("CO2-001");
        
    }
    
}