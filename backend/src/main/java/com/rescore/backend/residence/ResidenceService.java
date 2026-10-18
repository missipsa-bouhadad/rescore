package com.rescore.backend.residence;

import java.util.List;

import org.springframework.stereotype.Service;

// Read residences of a city
@Service
public class ResidenceService {

    private final ResidenceRepository repository;

    public ResidenceService(ResidenceRepository repository) {
        this.repository = repository;
    }

    public List<ResidenceResponse> findByCity(String citySlug) {
        return repository.findByCitySlugOrderByName(citySlug).stream()
                .map(ResidenceResponse::from)
                .toList();
    }
}
