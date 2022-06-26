package com.aiattoi.track.api.converter;

import com.aiattoi.track.api.dtos.TrackDto;
import com.aiattoi.track.domain.Track;

import java.util.ArrayList;
import java.util.Collection;

public class TrackConverter {
    public static Track fromDto(TrackDto trackDto) {
        Track track = new Track(trackDto.type, trackDto.color);
        if (trackDto.id == null)
            track.setId(0);
        else
            track.setId(trackDto.id);
        return track;
    }

    public static TrackDto toDto(Track track) {
        return new TrackDto(track.getId(), track.getType(), track.getColor());
    }

    public static Collection<Track> fromDtos(Collection<TrackDto> trackDtos) {
        Collection<Track> tracks = new ArrayList<>();
        trackDtos.forEach((t) -> tracks.add(fromDto(t)));
        return tracks;
    }

    public static Collection<TrackDto> toDtos(Collection<Track> tracks) {
        Collection<TrackDto> trackDtos = new ArrayList<>();
        tracks.forEach((t) -> trackDtos.add(toDto(t)));
        return trackDtos;
    }
}
