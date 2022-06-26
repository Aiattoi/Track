package com.aiattoi.track.api.dtos;

import com.aiattoi.track.domain.TrackColor;
import com.aiattoi.track.domain.TrackType;

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
}
