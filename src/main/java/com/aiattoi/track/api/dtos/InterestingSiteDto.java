package com.aiattoi.track.api.dtos;

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
}
