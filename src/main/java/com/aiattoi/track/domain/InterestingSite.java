package com.aiattoi.track.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.*;

@Entity(name = "interesting_site")
public class InterestingSite implements Serializable {

    @Id
    @GeneratedValue(generator = "interestingSite-seq-generator")
    @GenericGenerator(
            name = "interestingSite-seq-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "interestingSite_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id = 0;
    private String name;
    private Integer altitude;
    private String GPSDirection;
    private double GPSLatitude;
    private double GPSLongitude;

    @ManyToMany(mappedBy = "interestingSites")
    private Set<Track> tracks = new HashSet<>();

    public InterestingSite() {
    }

    public InterestingSite(String name, Integer altitude, String GPSDirection, double GPSLatitude, double GPSLongitude) {
        this.name = Objects.requireNonNull(name);
        this.altitude = altitude;
        this.GPSDirection = GPSDirection;
        this.GPSLatitude = GPSLatitude;
        this.GPSLongitude = GPSLongitude;
    }

    public static String getEntityName() {
        return "Interesting site";
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

    public Integer getAltitude() {
        return altitude;
    }

    public String getGPSDirection() {
        return GPSDirection;
    }

    public double getGPSLatitude() {
        return GPSLatitude;
    }

    public double getGPSLongitude() {
        return GPSLongitude;
    }

    public Collection<Track> getTracks() {
        return Collections.unmodifiableCollection(tracks);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void deleteTrack(Track track) {
        tracks.remove(track);
    }
}
