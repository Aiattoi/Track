package com.aiattoi.track.dao;

import com.aiattoi.track.domain.InterestingSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface InterestingSiteJpaRepository extends JpaRepository<InterestingSite, Integer> {

    @Query
    Collection<InterestingSite> getInterestingSitesByAltitudeGreaterThan(Integer altitude);
}
