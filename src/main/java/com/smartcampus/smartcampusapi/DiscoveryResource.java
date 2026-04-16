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
 * Adapted from the Oracle Java EE Tutorial HelloWorld JAX-RS example.
 * Available at: https://docs.oracle.com/javaee/7/tutorial/jaxrs.htm
 * Implements the root discovery endpoint for Part 1.2 of the spec.
 * Returns API metadata including version, contact, and HATEOAS resource links.
 * Changes from the Oracle example:
 * - Path changed to "/" for the root discovery endpoint
 * - Returns APPLICATION_JSON instead of text/plain
 * - Uses UriInfo to build dynamic resource links rather than hardcoded strings
 * - Follows HATEOAS principles (Fielding, 2000) so clients can navigate the API
 *   from a single entry point without relying on static documentation
 */

@Path("/")
public class DiscoveryResource {

    // Injected by JAX-RS to provide dynamic URI information at runtime
    // Used to build resource links that automatically reflect the server's base URL
    @Context
    private UriInfo context;

    public DiscoveryResource() {}

    /**
     * GET /api/v1
     * Returns essential API metadata including version, contact details,
     * and hypermedia links to the primary resource collections.
     */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApiInfo() {
        Map<String, Object> info = new HashMap<>();

        // API versioning and identification
        info.put("version", "1.0");
        info.put("name", "Smart Campus Sensor & Room Management API");

        // Administrative contact details
        info.put("contact", "admin@smartcampus.ac.uk");

        // Build dynamic resource links using UriInfo rather than hardcoded strings
        // This ensures links remain correct if the server port or context path changes
        String base = context.getBaseUri().toString();
        Map<String, String> resources = new HashMap<>();
        resources.put("rooms", base + "rooms");
        resources.put("sensors", base + "sensors");
        info.put("resources", resources);

        return Response.ok(info).build();
    }
    
}