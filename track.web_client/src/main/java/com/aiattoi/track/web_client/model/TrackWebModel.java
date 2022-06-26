package com.aiattoi.track.web_client.model;

public class TrackWebModel extends TrackDto{

    public TrackWebModel() {}

    public TrackWebModel(TrackDto trackDto) {
        super(trackDto.id, trackDto.type, trackDto.color);
    }
}
