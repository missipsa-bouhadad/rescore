package com.rescore.backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rescore.backend.residence.Residence;
import com.rescore.backend.residence.ResidenceRepository;
import com.rescore.backend.university.AllowedEmailDomain;
import com.rescore.backend.university.AllowedEmailDomainRepository;
import com.rescore.backend.university.EmailDomain;
import com.rescore.backend.university.University;
import com.rescore.backend.university.UniversityRepository;
import com.rescore.backend.user.User;
import com.rescore.backend.user.UserRepository;

// Create a new pending account
@Service
public class RegistrationService {

    private final UserRepository users;
    private final AllowedEmailDomainRepository domains;
    private final UniversityRepository universities;
    private final ResidenceRepository residences;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository users, AllowedEmailDomainRepository domains,
            UniversityRepository universities, ResidenceRepository residences, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.domains = domains;
        this.universities = universities;
        this.residences = residences;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        String email = request.email();

        // The email domain gives the university
        AllowedEmailDomain domain = domains.findByDomain(EmailDomain.extract(email)).orElseThrow(() -> badRequest("Email domain not allowed"));
        University university = universities.getReferenceById(domain.getUniversityId());

        // The residence must be in the university city
        Residence residence = residences.findById(request.residenceId()).orElseThrow(() -> badRequest("Unknown residence"));
        if (!residence.getCity().getId().equals(university.getCityId())) {
            throw badRequest("Residence is not in the university city");
        }

        // One account per email
        if (users.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already used");
        }

        User user = new User(university, residence, email,
                passwordEncoder.encode(request.password()), request.firstName().trim());
        return RegisterResponse.from(users.save(user));
    }

    private static ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
