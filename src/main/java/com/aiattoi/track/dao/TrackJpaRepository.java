package com.aiattoi.track.dao;

import com.aiattoi.track.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TrackJpaRepository extends JpaRepository<Track, Integer> {

    @Query
    Collection<Track> getTracksByManagerIsNull();
}
