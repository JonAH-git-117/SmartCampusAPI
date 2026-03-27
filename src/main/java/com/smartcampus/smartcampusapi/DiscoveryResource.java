package com.smartcampus.smartcampusapi;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
/**
 * DiscoveryResource
 * Adapted from the Oracle Java EE Tutorial HelloWorld JAX-RS example.
 * Available at: https://docs.oracle.com/javaee/7/tutorial/jaxrs.htm
 * 
 * Changes from the Oracle example:
 * - Path changed to "/" for root discovery endpoint as per spec Part 1.2
 * - Returns APPLICATION_JSON instead of text/html (more appropriate for an API)
 * - Uses UriInfo context (kept from Oracle example) to build dynamic resource links
 * - Returns API metadata following HATEOAS principles (Fielding, 2000)
 */
@Path("/")
public class DiscoveryResource {

    // Kept from Oracle example - provides URI context information
    @Context
    private UriInfo context;

    public DiscoveryResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApiInfo() {
        Map<String, Object> info = new HashMap<>();

        // API versioning information
        info.put("version", "1.0");
        info.put("name", "Smart Campus Sensor & Room Management API");

        // Administrative contact details
        info.put("contact", "admin@smartcampus.ac.uk");

        // Resource collection links - HATEOAS principle
        Map<String, String> resources = new HashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");
        info.put("resources", resources);

        return Response.ok(info).build();
    }
}