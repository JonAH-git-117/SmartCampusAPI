package com.smartcampus.smartcampusapi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
/**
 * MyApplication
 * Adapted from Week 7 tutorial's MyApplication.java
 * 
 * Defines the base path for all REST endpoints as /api/v1
 * as required by the spec.
 * 
 * Registers all resource classes so JAX-RS knows about them.
 */
@ApplicationPath("/api/v1")
public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);
        classes.add(DiscoveryResource.class);
        return classes;
    }
}