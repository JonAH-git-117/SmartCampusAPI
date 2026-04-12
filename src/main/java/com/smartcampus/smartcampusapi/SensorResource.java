package com.smartcampus.smartcampusapi;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * SensorResource
 * Adapted from Week 8 tutorial's StudentResource.java
 * Handles all HTTP requests for the /api/v1/sensors endpoint.
 * Covers Part 3 of the spec (Sensor Operations and Filtering).
 */
@Path("/sensors")
public class SensorResource {

    // Using GenericDAO with MockDatabase list - same pattern as tutorial
    private GenericDAO<Sensor> sensorDAO = new GenericDAO<>(MockDatabase.SENSORS);
    private GenericDAO<Room> roomDAO = new GenericDAO<>(MockDatabase.ROOMS);

    // GET /api/v1/sensors - returns all sensors
    // Supports optional ?type= query parameter for filtering
    // Adapted from tutorial's getAllStudents()
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        if (type != null && !type.isEmpty()) {
            List<Sensor> filtered = new ArrayList<>();
            for (Sensor sensor : sensorDAO.getAll()) {
                if (sensor.getType().equalsIgnoreCase(type)) {
                    filtered.add(sensor);
                }
            }
            return filtered;
        }
        return sensorDAO.getAll();
    }

    // GET /api/v1/sensors/{sensorId} - returns a specific sensor by ID
    // Adapted from tutorial's getStudentById()
    @GET
    @Path("/{sensorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorById(@PathParam("sensorId") String sensorId) {
        Sensor sensor = sensorDAO.getById(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }
        return Response.ok(sensor).build();
    }

    // POST /api/v1/sensors - registers a new sensor
    // Validates that the roomId exists before registering
    // Adapted from tutorial's addStudent()
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {
        // Validate that the roomId exists
        Room room = roomDAO.getById(sensor.getRoomId());
        if (room == null) {
            return Response.status(422)
                    .entity("{\"error\": \"Room with id '" + sensor.getRoomId() + "' does not exist\"}")
                    .build();
        }
        sensorDAO.add(sensor);
        // Add sensor ID to the room's sensor list
        room.getSensorIds().add(sensor.getId());
        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    // DELETE /api/v1/sensors/{sensorId} - deletes a sensor
    // Adapted from tutorial's deleteStudent()
    @DELETE
    @Path("/{sensorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSensor(@PathParam("sensorId") String sensorId) {
        Sensor sensor = sensorDAO.getById(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }
        // Remove sensor ID from its room's sensor list
        Room room = roomDAO.getById(sensor.getRoomId());
        if (room != null) {
            room.getSensorIds().remove(sensorId);
        }
        sensorDAO.delete(sensorId);
        return Response.noContent().build();
    }
    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingsResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}