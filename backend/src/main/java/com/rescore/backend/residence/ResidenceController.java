package com.rescore.backend.residence;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Endpoints for residences
@RestController
public class ResidenceController {

    private final ResidenceService service;

    public ResidenceController(ResidenceService service) {
        this.service = service;
    }

    // List residences of a city
    @GetMapping("/api/{city}/residences")
    public List<ResidenceResponse> list(@PathVariable String city) {
        return service.findByCity(city);
    }
}
