package com.rescore.backend.university;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Email domain allowed to register 
@Entity
@Table(name = "allowed_email_domains")
public class AllowedEmailDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "university_id", nullable = false)
    private Long universityId;

    @Column(nullable = false, unique = true)
    private String domain;

    protected AllowedEmailDomain() {
    }

    public Long getId() {
        return id;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public String getDomain() {
        return domain;
    }
}
