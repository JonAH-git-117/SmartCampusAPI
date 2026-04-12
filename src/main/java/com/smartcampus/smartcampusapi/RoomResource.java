package com.smartcampus.smartcampusapi;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RoomResource
 * Adapted from Week 8 tutorial's ModuleResource.java
 * Handles all HTTP requests for the /api/v1/rooms endpoint.
 * Covers Part 2 of the spec (Room Management).
 */
@Path("/rooms")
public class RoomResource {

    // Using GenericDAO with MockDatabase list - same pattern as tutorial
    private GenericDAO<Room> roomDAO = new GenericDAO<>(MockDatabase.ROOMS);

    // GET /api/v1/rooms - returns all rooms
    // Adapted from tutorial's getAllModules()
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return roomDAO.getAll();
    }

    // GET /api/v1/rooms/{roomId} - returns a specific room by ID
    // Adapted from tutorial's getModuleById()
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

    // POST /api/v1/rooms - creates a new room
    // Adapted from tutorial's addModule()
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        roomDAO.add(room);
        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    // DELETE /api/v1/rooms/{roomId} - deletes a room
    // Adapted from tutorial's deleteModule()
    // Business logic: cannot delete a room that still has sensors
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
        if (!room.getSensorIds().isEmpty()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"Room still has sensors assigned to it\"}")
                    .build();
        }
        roomDAO.delete(roomId);
        return Response.noContent().build();
    }
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
        updatedRoom.setId(roomId);
        roomDAO.update(updatedRoom);
        return Response.ok(updatedRoom).build();
    }
}