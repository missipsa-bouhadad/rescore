package com.rescore.backend.residence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidenceRepository extends JpaRepository<Residence, Long> {

    // Residences of a city, sorted by name
    List<Residence> findByCitySlugOrderByName(String citySlug);
}
