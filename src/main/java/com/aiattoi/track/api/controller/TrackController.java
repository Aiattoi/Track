package com.aiattoi.track.api.controller;

import com.aiattoi.track.api.converter.InterestingSiteConverter;
import com.aiattoi.track.api.converter.TrackConverter;
import com.aiattoi.track.api.dtos.InterestingSiteDto;
import com.aiattoi.track.api.dtos.ManagerDto;
import com.aiattoi.track.api.dtos.TrackDto;
import com.aiattoi.track.business.TrackService;
import com.aiattoi.track.api.converter.ManagerConverter;
import com.aiattoi.track.business.InterestingSiteService;
import com.aiattoi.track.business.NoEntityFoundException;
import com.aiattoi.track.domain.Track;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class TrackController {
    private final TrackService trackService;
    private final InterestingSiteService siteService;

    public TrackController(TrackService trackService, InterestingSiteService siteService) {
        this.trackService = trackService;
        this.siteService = siteService;
    }

    @GetMapping("/tracks")
    Collection<TrackDto> listAll() {
        return TrackConverter.toDtos(trackService.listAll());
    }

    @PostMapping("/tracks")
    TrackDto newTrack(@RequestBody TrackDto newTrack)
            throws NoEntityFoundException {

        Track t = trackService.create(TrackConverter.fromDto(newTrack));

        Track track = this.trackService.readById(t.getId()).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName()));

        return TrackConverter.toDto(track);
    }

    @GetMapping("/tracks/{id}")
    TrackDto listOne(@PathVariable Integer id)
            throws NoEntityFoundException {

        return TrackConverter.toDto(trackService.readById(id).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName())));
    }

    @PutMapping("/tracks/{id}")
    TrackDto updateTrack(@RequestBody TrackDto trackDto, @PathVariable Integer id) {
        trackDto.id = id;
        Track track = TrackConverter.fromDto(trackDto);

        trackService.updateWithSitesAndManager(siteService, track);

        return TrackConverter.toDto(track);
    }

    @GetMapping("tracks/{trackId}/manager")
    Optional<ManagerDto> getManager(@PathVariable Integer trackId)
            throws NoEntityFoundException {

        Track track = trackService.readById(trackId).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName()));

        if (track.getManager().isPresent())
            return Optional.of(ManagerConverter.toDto(track.getManager().get()));
        else
            return Optional.empty();
    }

    @PutMapping("/tracks/{trackId}/manager/{managerId}")
    TrackDto changeManager(@PathVariable Integer trackId, @PathVariable Integer managerId)
            throws NoEntityFoundException {

        Track track = trackService.readById(trackId).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName()));

        trackService.updateManager(track, managerId);

        return TrackConverter.toDto(track);
    }

    @DeleteMapping("/tracks/{trackId}/manager")
    TrackDto deleteManager(@PathVariable Integer trackId)
            throws NoEntityFoundException {

        Track track = trackService.readById(trackId).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName()));

        trackService.deleteManager(track);

        return TrackConverter.toDto(track);
    }

    @GetMapping("/tracks/{id}/interestingSites")
    Collection<InterestingSiteDto> listSites(@PathVariable Integer id)
            throws NoEntityFoundException {

        Track track = trackService.readById(id).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName()));

        return InterestingSiteConverter.toDtos(track.getInterestingSites());
    }

    @PutMapping("/tracks/{trackId}/interestingSites/{isId}")
    TrackDto addInterestingSite(@PathVariable Integer trackId, @PathVariable Integer isId)
            throws NoEntityFoundException {

        Track track = trackService.readById(trackId).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName()));

        trackService.addSite(siteService, track, isId);

        return TrackConverter.toDto(track);
    }

    @PutMapping("/tracks/{trackId}/interestingSites")
    TrackDto addInterestingSites(@PathVariable Integer trackId, @RequestBody List<Integer> interestingSitesIds)
            throws NoEntityFoundException {

        Track track = trackService.readById(trackId).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName()));

        for (Integer siteId : interestingSitesIds) {
            trackService.addSite(siteService, track, siteId);
        }
        return TrackConverter.toDto(track);
    }

    @DeleteMapping("/tracks/{trackId}/interestingSites/{isId}")
    TrackDto deleteInterestingSite(@PathVariable Integer trackId, @PathVariable Integer isId)
            throws NoEntityFoundException {

        Track track = trackService.readById(trackId).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName()));

        trackService.deleteSite(siteService, track, isId);

        return TrackConverter.toDto(track);
    }

    @DeleteMapping("tracks/{id}")
    void deleteTrack(@PathVariable Integer id) {
        trackService.deleteByIdWithSitesAndManager(siteService, id);
    }

    @GetMapping("tracks/manager/null")
    Collection<TrackDto> listTracksWithoutManagers() {
        return TrackConverter.toDtos(trackService.listTracksWithoutManager());
    }
}
