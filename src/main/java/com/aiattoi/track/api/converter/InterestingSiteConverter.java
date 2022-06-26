package com.aiattoi.track.api.converter;

import com.aiattoi.track.api.dtos.InterestingSiteDto;
import com.aiattoi.track.domain.InterestingSite;

import java.util.ArrayList;
import java.util.Collection;

public class InterestingSiteConverter {
    public static InterestingSite fromDto(InterestingSiteDto siteDto) {
        InterestingSite site = new InterestingSite(siteDto.name, siteDto.altitude, siteDto.GPSDirection, siteDto.GPSLatitude, siteDto.GPSLongitude);
        if (siteDto.id == null)
            site.setId(0);
        else
            site.setId(siteDto.id);
        return site;
    }

    public static InterestingSiteDto toDto(InterestingSite site) {
        return new InterestingSiteDto(site.getId(), site.getName(), site.getAltitude(), site.getGPSDirection(), site.getGPSLatitude(), site.getGPSLongitude());
    }

    public static Collection<InterestingSite> fromDtos(Collection<InterestingSiteDto> siteDtos) {
        Collection<InterestingSite> sites = new ArrayList<>();
        siteDtos.forEach((isD) -> sites.add(fromDto(isD)));
        return sites;
    }

    public static Collection<InterestingSiteDto> toDtos(Collection<InterestingSite> sites) {
        Collection<InterestingSiteDto> siteDtos = new ArrayList<>();
        sites.forEach((is) -> siteDtos.add(toDto(is)));
        return siteDtos;
    }
}
