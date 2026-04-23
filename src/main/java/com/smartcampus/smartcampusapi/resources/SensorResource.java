package com.smartcampus.smartcampusapi.resources;

import com.smartcampus.smartcampusapi.resources.SensorReadingResource;
import com.smartcampus.smartcampusapi.exception.LinkedResourceNotFoundException;
import com.smartcampus.smartcampusapi.dao.MockDatabase;
import com.smartcampus.smartcampusapi.dao.GenericDAO;
import com.smartcampus.smartcampusapi.model.Sensor;
import com.smartcampus.smartcampusapi.model.Room;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Adapted from Tutorial Week 08 (StudentResource.java)
 * Handles all HTTP requests for the /api/v1/sensors endpoint.
 * Covers Part 3 of the spec (Sensor Operations and Filtering).
 * Implements CRUD operations for Sensor entities and supports optional
 * filtering by type via query parameter. Also acts as the entry point
 * for the sub-resource locator pattern used in Part 4.
 */

@Path("/sensors")
public class SensorResource {

    // GenericDAO wired to the shared static lists in MockDatabase
    // Same pattern as tutorial's StudentResource
    private GenericDAO<Sensor> sensorDAO = new GenericDAO<>(MockDatabase.SENSORS);
    private GenericDAO<Room> roomDAO = new GenericDAO<>(MockDatabase.ROOMS);

    /**
     * GET /api/v1/sensors
     * Returns all sensors, with optional case-insensitive filtering by type.
     * If the ?type= query parameter is provided, only matching sensors are returned.
     * Adapted from tutorial's getAllStudents()
     */
    
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

    /**
     * GET /api/v1/sensors/{sensorId}
     * Returns a single sensor by its unique ID.
     * Returns 404 if the sensor does not exist.
     * Adapted from tutorial's getStudentById()
     */
    
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

    /**
     * POST /api/v1/sensors
     * Registers a new sensor in the system.
     * Validates that the roomId in the request body exists before registering.
     * Throws LinkedResourceNotFoundException (422) if the room does not exist.
     * Also updates the parent room's sensorIds list to maintain data consistency.
     * Adapted from tutorial's addStudent()
     */
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {
        
        // Validate that the referenced room exists before registering the sensor
        Room room = roomDAO.getById(sensor.getRoomId());
        if (room == null) {
            
            throw new LinkedResourceNotFoundException("Room with id '" + sensor.getRoomId() + "' does not exist.");
            
        }
        sensorDAO.add(sensor);

        // Keep the room's sensor list in sync with the new sensor
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    /**
     * DELETE /api/v1/sensors/{sensorId}
     * Removes a sensor from the system.
     * Also removes the sensor ID from its parent room's sensorIds list
     * to maintain referential integrity across the in-memory data store.
     * Adapted from tutorial's deleteStudent()
     */
    
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
        
        // Remove this sensor's ID from its parent room's sensor list
        Room room = roomDAO.getById(sensor.getRoomId());
        if (room != null) {
            room.getSensorIds().remove(sensorId);
        }
        sensorDAO.delete(sensorId);
        return Response.noContent().build();
        
    }

    /**
     * Sub-resource locator for /api/v1/sensors/{sensorId}/readings
     * Delegates all reading-related requests to SensorReadingResource.
     * This implements the Sub-Resource Locator pattern from Part 4 of the spec,
     * keeping reading logic separate from sensor logic for maintainability.
     */
    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingsResource(@PathParam("sensorId") String sensorId) {
        
        return new SensorReadingResource(sensorId);
        
    }
    
}