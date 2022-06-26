package com.aiattoi.track.web_client.model;

public class InterestingSiteDto {
    public Integer id;
    public String name;
    public Integer altitude;
    public String GPSDirection;
    public double GPSLatitude;
    public double GPSLongitude;

    public InterestingSiteDto() {
    }

    public InterestingSiteDto(String name, Integer altitude, String GPSDirection, double GPSLatitude, double GPSLongitude) {
        this.name = name;
        this.altitude = altitude;
        this.GPSDirection = GPSDirection;
        this.GPSLatitude = GPSLatitude;
        this.GPSLongitude = GPSLongitude;
    }

    public InterestingSiteDto(Integer id, String name, Integer altitude, String GPSDirection, double GPSLatitude, double GPSLongitude) {
        this.id = id;
        this.name = name;
        this.altitude = altitude;
        this.GPSDirection = GPSDirection;
        this.GPSLatitude = GPSLatitude;
        this.GPSLongitude = GPSLongitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public String getGPSDirection() {
        return GPSDirection;
    }

    public void setGPSDirection(String GPSDirection) {
        this.GPSDirection = GPSDirection;
    }

    public double getGPSLatitude() {
        return GPSLatitude;
    }

    public void setGPSLatitude(double GPSLatitude) {
        this.GPSLatitude = GPSLatitude;
    }

    public double getGPSLongitude() {
        return GPSLongitude;
    }

    public void setGPSLongitude(double GPSLongitude) {
        this.GPSLongitude = GPSLongitude;
    }
}
