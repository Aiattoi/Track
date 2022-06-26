package com.aiattoi.track.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "manager")
public class Manager implements Serializable {
    @Id
    @GeneratedValue(generator = "manager-seq-generator")
    @GenericGenerator(
            name = "manager-seq-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "manager_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id = 0;
    private String name;
    @OneToMany(mappedBy = "manager")
    List<Track> tracks = new ArrayList<>();

    public Manager() {
    }

    public Manager(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public static String getEntityName() {
        return "Manager";
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

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void deleteTrack(Track track) {
        tracks.remove(track);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tracks=" + tracks +
                '}';
    }
}
