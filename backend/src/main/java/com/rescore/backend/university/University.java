package com.rescore.backend.university;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// University of a city
@Entity
@Table(name = "universities")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_id", nullable = false)
    private Long cityId;

    @Column(nullable = false)
    private String name;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    protected University() {
    }

    public Long getId() {
        return id;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
