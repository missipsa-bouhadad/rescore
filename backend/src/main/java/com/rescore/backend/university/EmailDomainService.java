package com.rescore.backend.university;

import org.springframework.stereotype.Service;

// Check if an email belongs to an allowed university domain
@Service
public class EmailDomainService {

    private final AllowedEmailDomainRepository repository;

    public EmailDomainService(AllowedEmailDomainRepository repository) {
        this.repository = repository;
    }

    public boolean isAllowed(String email) {
        String domain;
        try {
            domain = EmailDomain.extract(email);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return repository.existsByDomain(domain);
    }
}
