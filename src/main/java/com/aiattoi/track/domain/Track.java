package com.aiattoi.track.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "track")
public class Track implements Serializable {
    @Id
    @GeneratedValue(generator = "track-seq-generator")
    @GenericGenerator(
            name = "track-seq-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "track_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id = 0;
    private TrackType type;
    private TrackColor color;

    @ManyToOne
    @JoinColumn(name = "track_manager")
    Manager manager;

    @ManyToMany
    @JoinTable(name = "interesting_site_track",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "interesting_site_id"))
    private Set<InterestingSite> interestingSites = new HashSet<>();

    public Track() {
    }

    public Track(TrackType type, TrackColor color) {
        this.type = type;
        this.color = color;
    }

    public static String getEntityName() {
        return "Track";
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

    public TrackColor getColor() {
        return color;
    }

    public Optional<Manager> getManager() {
        if (manager == null)
            return Optional.empty();
        return Optional.of(manager);
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void deleteManager() {
        setManager(null);
    }

    public Collection<InterestingSite> getInterestingSites() {
        return Collections.unmodifiableCollection(interestingSites);
    }

    public void addSite(InterestingSite site) {
        interestingSites.add(site);
    }

    public void removeSite(InterestingSite site) {
        interestingSites.remove(site);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", type=" + type +
                ", color=" + color +
                ", manager=" + manager +
                ", interestingSites=" + interestingSites +
                '}';
    }
}
