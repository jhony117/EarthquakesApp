package com.example.eathquakemonitor.api;

public class Feature {

    private String id;
    private Properties properties;
    private Geometry geometry;

    public String getId() {
        return id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public Properties getProperties() {
        return properties;
    }
}
