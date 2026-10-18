package com.rescore.backend.residence;

// Residence data sent to the front
public record ResidenceResponse(Long id, String name, String address, ResidenceType type) {

    public static ResidenceResponse from(Residence residence) {
        return new ResidenceResponse(
                residence.getId(),
                residence.getName(),
                residence.getAddress(),
                residence.getType());
    }
}
