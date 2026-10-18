package com.rescore.backend.residence;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// Endpoints for residences
@Tag(name = "Residences")
@RestController
public class ResidenceController {

    private final ResidenceService service;

    public ResidenceController(ResidenceService service) {
        this.service = service;
    }

    // List residences of a city
    @Operation(summary = "List residences of a city, sorted by name")
    @GetMapping("/api/{city}/residences")
    public List<ResidenceResponse> list(@Parameter(description = "City slug", example = "pau") @PathVariable String city) {
        return service.findByCity(city);
    }
}
