package com.smartcampus.smartcampusapi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * SensorReadingResource
 * Sub-resource class handling reading history for a specific sensor.
 * Accessed via SensorResource's sub-resource locator.
 * Covers Part 4 of the spec (Deep Nesting with Sub-Resources).
 */
public class SensorReadingResource {

    private final String sensorId;
    private GenericDAO<Sensor> sensorDAO = new GenericDAO<>(MockDatabase.SENSORS);
    private GenericDAO<SensorReading> readingDAO = new GenericDAO<>(MockDatabase.READINGS);

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    // GET /api/v1/sensors/{sensorId}/readings
    // Returns all historical readings for this sensor
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        Sensor sensor = sensorDAO.getById(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }
        List<SensorReading> history = new ArrayList<>();
        for (SensorReading reading : readingDAO.getAll()) {
            if (reading.getSensorId().equals(sensorId)) {
                history.add(reading);
            }
        }
        return Response.ok(history).build();
    }

    // POST /api/v1/sensors/{sensorId}/readings
    // Adds a new reading and updates the sensor's currentValue
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
        // Auto-generate ID and timestamp if not provided
        if (reading.getId() == null || reading.getId().isEmpty()) {
            reading.setId(UUID.randomUUID().toString());
        }
        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }
        reading.setSensorId(sensorId);
        readingDAO.add(reading);

        // Side effect: update parent sensor's currentValue
        sensor.setCurrentValue(reading.getValue());
        sensorDAO.update(sensor);

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}