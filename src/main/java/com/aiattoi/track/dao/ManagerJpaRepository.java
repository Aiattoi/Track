package com.aiattoi.track.dao;

import com.aiattoi.track.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerJpaRepository extends JpaRepository<Manager, Integer> {

}
