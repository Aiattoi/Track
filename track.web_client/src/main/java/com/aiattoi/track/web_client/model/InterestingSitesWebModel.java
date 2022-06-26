package com.aiattoi.track.web_client.model;

public class InterestingSitesWebModel extends InterestingSiteDto{

    public InterestingSitesWebModel() {}

    public InterestingSitesWebModel(InterestingSiteDto siteDto) {
        super(siteDto.id, siteDto.name, siteDto.altitude, siteDto.GPSDirection, siteDto.GPSLatitude, siteDto.GPSLongitude);
    }
}
