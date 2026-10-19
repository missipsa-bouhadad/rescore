package com.rescore.backend.user;

import java.time.Instant;

import com.rescore.backend.residence.Residence;
import com.rescore.backend.university.University;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// User 
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residence_id", nullable = false)
    private Residence residence;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    protected User() {
    }

    // New account with pending status until the email is confirmed
    public User(University university, Residence residence, String email, String passwordHash, String firstName) {
        this.university = university;
        this.residence = residence;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.status = UserStatus.PENDING;
        this.role = UserRole.PLAYER;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public University getUniversity() {
        return university;
    }

    public Residence getResidence() {
        return residence;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserRole getRole() {
        return role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
