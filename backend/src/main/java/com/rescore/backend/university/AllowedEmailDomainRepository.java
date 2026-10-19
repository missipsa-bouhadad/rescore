package com.rescore.backend.university;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowedEmailDomainRepository extends JpaRepository<AllowedEmailDomain, Long> {

    // Return true if domain is in the table
    boolean existsByDomain(String domain);

    Optional<AllowedEmailDomain> findByDomain(String domain);
}