package com.aiattoi.track.api.controller;

import com.aiattoi.track.api.converter.InterestingSiteConverter;
import com.aiattoi.track.api.converter.TrackConverter;
import com.aiattoi.track.api.dtos.InterestingSiteDto;
import com.aiattoi.track.api.dtos.TrackDto;
import com.aiattoi.track.business.TrackService;
import com.aiattoi.track.business.InterestingSiteService;
import com.aiattoi.track.business.NoEntityFoundException;
import com.aiattoi.track.domain.InterestingSite;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class InterestingSiteController {
    private final InterestingSiteService interestingSiteService;
    private final TrackService trackService;

    public InterestingSiteController(InterestingSiteService interestingSiteService, TrackService trackService) {
        this.interestingSiteService = interestingSiteService;
        this.trackService = trackService;
    }

    @GetMapping("/interestingSites")
    Collection<InterestingSiteDto> listAll() {

        return InterestingSiteConverter.toDtos(interestingSiteService.listAll());
    }

    @PostMapping("/interestingSites")
    InterestingSiteDto newInterestingSite(@RequestBody InterestingSiteDto newSite)
            throws NoEntityFoundException {

        InterestingSite site;

        site = interestingSiteService.create(InterestingSiteConverter.fromDto(newSite));

        return InterestingSiteConverter.toDto(this.interestingSiteService.readById(site.getId()).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName())));
    }

    @GetMapping("/interestingSites/{id}")
    InterestingSiteDto listOne(@PathVariable Integer id)
            throws NoEntityFoundException {

        return InterestingSiteConverter.toDto(interestingSiteService.readById(id).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName())));
    }

    @PutMapping("/interestingSites/{id}")
    InterestingSiteDto updateSite(@RequestBody InterestingSiteDto siteDto, @PathVariable Integer id)
            throws NoEntityFoundException {

        siteDto.id = id;

        interestingSiteService.updateWithTracks(trackService, InterestingSiteConverter.fromDto(siteDto));

        return InterestingSiteConverter.toDto(interestingSiteService.readById(siteDto.id).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName())));
    }

    @GetMapping("/interestingSites/{id}/tracks")
    Collection<TrackDto> listTracks(@PathVariable Integer id)
            throws NoEntityFoundException {

        InterestingSite site = interestingSiteService.readById(id).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName()));

        return TrackConverter.toDtos(site.getTracks());
    }

    @PutMapping("/interestingSites/{isId}/tracks/{trackId}")
    InterestingSiteDto addTrack(@PathVariable Integer isId, @PathVariable Integer trackId)
            throws NoEntityFoundException {

        InterestingSite site = interestingSiteService.readById(isId).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName()));

        interestingSiteService.addTrack(trackService, site, trackId);

        return InterestingSiteConverter.toDto(site);
    }


    @PutMapping("/interestingSites/{isId}/tracks")
    InterestingSiteDto addTracks(@PathVariable Integer isId, @RequestBody List<Integer> tracksIds)
            throws NoEntityFoundException {

        InterestingSite site = interestingSiteService.readById(isId).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName()));

        for (Integer trackId : tracksIds)
            interestingSiteService.addTrack(trackService, site, trackId);

        return InterestingSiteConverter.toDto(site);
    }

    @DeleteMapping("/interestingSites/{isId}/tracks/{trackId}")
    InterestingSiteDto deleteTrack(@PathVariable Integer isId, @PathVariable Integer trackId)
            throws NoEntityFoundException {

        InterestingSite site = interestingSiteService.readById(isId).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName()));

        interestingSiteService.deleteTrack(trackService, site, trackId);

        return InterestingSiteConverter.toDto(site);
    }

    @DeleteMapping("/interestingSites/{id}")
    void deleteSite(@PathVariable Integer id)
            throws NoEntityFoundException {

        interestingSiteService.readById(id).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName()));

        interestingSiteService.deleteByIdWithTracks(trackService, id);
    }

    @GetMapping("interestingSites/greater/{altitude}")
    Collection<InterestingSiteDto> listSitesGreaterThan(@PathVariable Integer altitude) {
        return InterestingSiteConverter.toDtos(interestingSiteService.listInterestingSitesGreaterThan(altitude));
    }
}
