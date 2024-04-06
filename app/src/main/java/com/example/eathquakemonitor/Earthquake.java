package com.example.eathquakemonitor;

public class Earthquake {

    private String id;
    private String place ;
    private double magnitude;
    private long time;
    private double latitude;
    private double longitude ;

    public Earthquake(String id, String  place, double magnitude, long time, double latitude, double longitude){
        this.id = id;
        this.place = place;
        this.magnitude = magnitude;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public long getTime() {
        return time;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
