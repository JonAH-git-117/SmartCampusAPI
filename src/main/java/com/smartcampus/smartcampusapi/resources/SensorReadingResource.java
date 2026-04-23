package com.smartcampus.smartcampusapi.resources;

import com.smartcampus.smartcampusapi.exception.SensorUnavailableException;
import com.smartcampus.smartcampusapi.dao.MockDatabase;
import com.smartcampus.smartcampusapi.dao.GenericDAO;
import com.smartcampus.smartcampusapi.model.SensorReading;
import com.smartcampus.smartcampusapi.model.Sensor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Sub-resource class handling reading history for a specific sensor.
 * Accessed exclusively via SensorResource's sub-resource locator method,
 * not registered directly with JAX-RS — no @Path annotation at class level.
 * Covers Part 4 of the spec (Deep Nesting with Sub-Resources).
 * This class demonstrates the Sub-Resource Locator pattern, where delegating
 * nested resource logic to a dedicated class keeps each class focused
 * on a single responsibility and reduces complexity as the API grows.
 */

public class SensorReadingResource {

    // The sensorId this resource instance is scoped to
    // Passed in from SensorResource's sub-resource locator
    private final String sensorId;

    // DAOs wired to the shared static lists in MockDatabase
    private GenericDAO<Sensor> sensorDAO = new GenericDAO<>(MockDatabase.SENSORS);
    private GenericDAO<SensorReading> readingDAO = new GenericDAO<>(MockDatabase.READINGS);

    /**
     * Constructor called by SensorResource's sub-resource locator.
     * Scopes this resource instance to a specific sensor.
     * @param sensorId the ID of the sensor whose readings are being managed
     */
    
    public SensorReadingResource(String sensorId) {
        
        this.sensorId = sensorId;
        
    }

    /**
     * GET /api/v1/sensors/{sensorId}/readings
     * Returns the full historical reading log for this sensor.
     * Filters the shared READINGS list to only return readings for this sensor.
     * Returns 404 if the parent sensor does not exist.
     */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        
        Sensor sensor = sensorDAO.getById(sensorId);
        
        if (sensor == null) {
            
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }
        
        // Filter all readings down to only those belonging to this sensor
        List<SensorReading> history = new ArrayList<>();
        for (SensorReading reading : readingDAO.getAll()) {
            
            if (reading.getSensorId().equals(sensorId)) {
                
                history.add(reading);
            }
            
        }
        
        return Response.ok(history).build();
        
    }

    /**
     * POST /api/v1/sensors/{sensorId}/readings
     * Appends a new reading to this sensor's history.
     * Throws SensorUnavailableException (403) if the sensor is in MAINTENANCE status.
     * Auto-generates a UUID and epoch timestamp if not provided by the client.
     * Side effect: updates the parent sensor's currentValue field to reflect
     * the most recent reading, ensuring data consistency across the API.
     */
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        
        Sensor sensor = sensorDAO.getById(sensorId);
        
        if (sensor == null) {
            
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }
        
        // Block readings for sensors currently under maintenance
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            
            throw new SensorUnavailableException("Sensor '" + sensorId + "' is currently under maintenance and cannot accept new readings.");
            
        }

        // Auto-generate ID and timestamp if not provided by the client
        if (reading.getId() == null || reading.getId().isEmpty()) {
            
            reading.setId(UUID.randomUUID().toString());
            
        }
        if (reading.getTimestamp() == 0) {
            
            reading.setTimestamp(System.currentTimeMillis());
        }
        
        reading.setSensorId(sensorId);
        readingDAO.add(reading);

        // Side effect: update parent sensor's currentValue to the latest reading
        sensor.setCurrentValue(reading.getValue());
        sensorDAO.update(sensor);

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
    
}