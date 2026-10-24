package com.rescore.backend.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.rescore.backend.city.City;
import com.rescore.backend.residence.Residence;
import com.rescore.backend.residence.ResidenceRepository;
import com.rescore.backend.university.AllowedEmailDomain;
import com.rescore.backend.university.AllowedEmailDomainRepository;
import com.rescore.backend.university.University;
import com.rescore.backend.university.UniversityRepository;
import com.rescore.backend.user.User;
import com.rescore.backend.user.UserRepository;
import com.rescore.backend.user.UserStatus;

// Tests the sign up rules
class RegistrationServiceTest {

    private UserRepository users;
    private ApplicationEventPublisher events;
    private RegistrationService service;

    // domain etud.univ-pau.fr -> university 1 (UPPA) -> city 1 (Pau)
    // residence 10 is in Pau, residence 20 is in another city
    @BeforeEach
    void setUp() {
        users = mock(UserRepository.class);
        AllowedEmailDomainRepository domains = mock(AllowedEmailDomainRepository.class);
        UniversityRepository universities = mock(UniversityRepository.class);
        ResidenceRepository residences = mock(ResidenceRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        VerificationTokenService tokens = mock(VerificationTokenService.class);
        events = mock(ApplicationEventPublisher.class);

        // The domain belongs to university 1
        AllowedEmailDomain domain = mock(AllowedEmailDomain.class);
        when(domain.getUniversityId()).thenReturn(1L);
        when(domains.findByDomain("etud.univ-pau.fr")).thenReturn(Optional.of(domain));

        // University 1 is in city 1
        University university = mock(University.class);
        when(university.getCityId()).thenReturn(1L);
        when(universities.getReferenceById(1L)).thenReturn(university);

        Residence inPau = residenceInCity(1L);
        Residence elsewhere = residenceInCity(2L);
        when(residences.findById(10L)).thenReturn(Optional.of(inPau));
        when(residences.findById(20L)).thenReturn(Optional.of(elsewhere));

        when(passwordEncoder.encode("password123")).thenReturn("hashed");

        // The token service always gives "token123"
        when(tokens.createFor(any(User.class))).thenReturn("token123");

        service = new RegistrationService(users, domains, universities, residences, passwordEncoder, tokens, events);
    }

    // User saved with the email, the hashed password and the PENDING status
    @Test
    void createsPendingUser() {
        service.register(request("jean@etud.univ-pau.fr", 10L));

        ArgumentCaptor<User> saved = ArgumentCaptor.forClass(User.class);
        verify(users).save(saved.capture());
        assertThat(saved.getValue().getEmail()).isEqualTo("jean@etud.univ-pau.fr");
        assertThat(saved.getValue().getPasswordHash()).isEqualTo("hashed");
        assertThat(saved.getValue().getStatus()).isEqualTo(UserStatus.PENDING);
    }

    // After sign up, the event to send the email is published
    @Test
    void asksForConfirmationEmail() {
        service.register(request("jean@etud.univ-pau.fr", 10L));

        verify(events).publishEvent(new UserRegisteredEvent("jean@etud.univ-pau.fr", "Jean", "token123"));
    }

    // The domain must match exactly
    @Test
    void rejectsUnknownDomain() {
        assertStatus(() -> service.register(request("x@etud.univ-pau.fr.lorem.com", 10L)), HttpStatus.BAD_REQUEST);
    }

    // Residence 99 does not exist
    @Test
    void rejectsUnknownResidence() {
        assertStatus(() -> service.register(request("jean@etud.univ-pau.fr", 99L)), HttpStatus.BAD_REQUEST);
    }

    // Residence 20 is not in the city of the university
    @Test
    void rejectsResidenceInAnotherCity() {
        assertStatus(() -> service.register(request("jean@etud.univ-pau.fr", 20L)), HttpStatus.BAD_REQUEST);
    }

    // Email already used (error 409 and nothing is saved)
    @Test
    void rejectsEmailAlreadyUsed() {
        when(users.existsByEmail("jean@etud.univ-pau.fr")).thenReturn(true);

        assertStatus(() -> service.register(request("jean@etud.univ-pau.fr", 10L)), HttpStatus.CONFLICT);
        verify(users, never()).save(any(User.class));
    }

    // Sign up request with a fixed first name and password
    private static RegisterRequest request(String email, Long residenceId) {
        return new RegisterRequest("Jean", email, "password123", residenceId);
    }

    // Fake residence located in the given city
    private static Residence residenceInCity(Long cityId) {
        City city = mock(City.class);
        when(city.getId()).thenReturn(cityId);
        Residence residence = mock(Residence.class);
        when(residence.getCity()).thenReturn(city);
        return residence;
    }

    // Checks that the call fails with the given HTTP status
    private static void assertStatus(Runnable call, HttpStatus status) {
        assertThatThrownBy(call::run).isInstanceOfSatisfying(ResponseStatusException.class,
            e -> assertThat(e.getStatusCode()).isEqualTo(status)
        );
    }
}
