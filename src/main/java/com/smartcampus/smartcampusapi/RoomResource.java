package com.smartcampus.smartcampusapi;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Adapted from Tutorial Week 08 (ModuleResource.java)
 * Handles all HTTP requests for the /api/v1/rooms endpoint.
 * Covers Part 2 of the spec (Room Management).
 * Implements full CRUD operations for Room entities stored in MockDatabase.
 * DELETE is protected by a business logic constraint — a room cannot be
 * decommissioned while it still has active sensors assigned to it.
 */

@Path("/rooms")
public class RoomResource {

    // GenericDAO wired to the shared static ROOMS list in MockDatabase
    // Same pattern as tutorial's ModuleResource
    private GenericDAO<Room> roomDAO = new GenericDAO<>(MockDatabase.ROOMS);

    /**
     * GET /api/v1/rooms
     * Returns a full list of all rooms currently in the system.
     * Adapted from tutorial's getAllModules()
     */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        
        return roomDAO.getAll();
        
    }

    /**
     * GET /api/v1/rooms/{roomId}
     * Returns a single room by its unique ID.
     * Returns 404 if the room does not exist.
     * Adapted from tutorial's getModuleById()
     */
    
    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomById(@PathParam("roomId") String roomId) {
        
        Room room = roomDAO.getById(roomId);
        
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Room not found\"}")
                    .build();
        }
        
        return Response.ok(room).build();
        
    }

    /**
     * POST /api/v1/rooms
     * Creates and registers a new room in the system.
     * Returns 201 Created with the new room object in the response body.
     * Adapted from tutorial's addModule()
     */
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        
        roomDAO.add(room);
        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
        
    }

    /**
     * DELETE /api/v1/rooms/{roomId}
     * Decommissions a room from the system.
     * Business logic constraint: throws RoomNotEmptyException (409 Conflict)
     * if the room still has sensors assigned to it, preventing data orphans.
     * Adapted from tutorial's deleteModule()
     */
    
    @DELETE
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        
        Room room = roomDAO.getById(roomId);
        
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Room not found\"}")
                    .build();
        }
        
        // Block deletion if sensors are still assigned to this room
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room '" + roomId + "' cannot be deleted as it still has sensors assigned to it.");
        }
        roomDAO.delete(roomId);
        return Response.noContent().build();
        
    }

    /**
     * PUT /api/v1/rooms/{roomId}
     * Fully replaces an existing room's details.
     * Returns 404 if the room does not exist.
     * Adapted from tutorial's updateModule()
     */
    
    @PUT
    @Path("/{roomId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoom(@PathParam("roomId") String roomId, Room updatedRoom) {
        Room existing = roomDAO.getById(roomId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Room not found\"}")
                    .build();
        }
        
        // Ensure the ID in the path is used, not whatever was in the request body
        updatedRoom.setId(roomId);
        roomDAO.update(updatedRoom);
        return Response.ok(updatedRoom).build();
        
    }
    
}