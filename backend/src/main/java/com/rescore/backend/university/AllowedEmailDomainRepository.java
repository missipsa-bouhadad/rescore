package com.rescore.backend.university;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowedEmailDomainRepository extends JpaRepository<AllowedEmailDomain, Long> {

    // Return true if domain is in the table
    boolean existsByDomain(String domain);
}