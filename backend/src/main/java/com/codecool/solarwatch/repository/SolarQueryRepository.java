package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.SolarQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolarQueryRepository extends JpaRepository<SolarQuery, Long> {
    List<SolarQuery> findTop20ByUserUsernameOrderByCreatedAtDesc(String username);
}
