package com.smartcampus.smartcampusapi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Adapted from Tutorial Week 07 (MyApplication.java)
 * Defines the versioned base path for all REST endpoints as /api/v1
 * as required by the spec (Part 1.1).
 * Registers all JAX-RS resource classes explicitly so the runtime
 * knows which classes to manage. Exception mappers and filters
 * annotated with @Provider are discovered automatically via
 * the web.xml package scan configuration.
 * Note: SensorReadingResource is not registered here as it is
 * a sub-resource accessed via SensorResource's locator method,
 * not a root resource.
 */

@ApplicationPath("/api/v1")
public class MyApplication extends Application {

    /**
     * Returns the set of root resource classes to be managed by JAX-RS.
     * Adapted from Tutorial Week 07's MyApplication.getClasses()
     */
    
    @Override
    public Set<Class<?>> getClasses() {
        
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DiscoveryResource.class);
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);
        return classes;
        
    }
    
}