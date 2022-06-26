package com.aiattoi.track.web_client.model;

public class TrackDto {
    public Integer id;
    public TrackType type;
    public TrackColor color;

    public TrackDto() {
    }

    public TrackDto(TrackType type, TrackColor color) {
        this.type = type;
        this.color = color;
    }

    public TrackDto(Integer id, TrackType type, TrackColor color) {
        this.id = id;
        this.type = type;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TrackType getType() {
        return type;
    }

    public void setType(TrackType type) {
        this.type = type;
    }

    public TrackColor getColor() {
        return color;
    }

    public void setColor(TrackColor color) {
        this.color = color;
    }
}
