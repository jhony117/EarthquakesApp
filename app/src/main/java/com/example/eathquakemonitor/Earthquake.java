package com.example.eathquakemonitor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Double.compare(magnitude, that.magnitude) == 0 &&
                time == that.time && Double.compare(latitude, that.latitude) == 0 &&
                Double.compare(longitude, that.longitude) == 0 && id.equals(that.id) &&
                place.equals(that.place);
    }
//Objects.equals(id, that.id), maneja nul sin lanzar una exepcion
    @Override
    public int hashCode() {
        return Objects.hash(id, place, magnitude, time, latitude, longitude);
    }
}
