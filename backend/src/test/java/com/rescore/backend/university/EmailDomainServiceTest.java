package com.rescore.backend.university;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests that only emails with an allowed domain can register
class EmailDomainServiceTest {

    private AllowedEmailDomainRepository repository;
    private EmailDomainService service;

    @BeforeEach
    void setUp() {
        repository = mock(AllowedEmailDomainRepository.class);
        when(repository.existsByDomain("etud.univ-pau.fr")).thenReturn(true);
        service = new EmailDomainService(repository);
    }

    @Test
    void acceptsAllowedDomain() {
        assertThat(service.isAllowed("jean@etud.univ-pau.fr")).isTrue();
    }

    @Test
    void acceptsUppercaseEmail() {
        assertThat(service.isAllowed("Jean@ETUD.UNIV-PAU.FR")).isTrue();
    }

    @Test
    void rejectsUnknownDomain() {
        assertThat(service.isAllowed("jean@gmail.com")).isFalse();
        assertThat(service.isAllowed("jean@anything")).isFalse();
    }

    @Test
    void rejectsInvalidEmailWithoutQuery() {
        assertThat(service.isAllowed("not-an-email")).isFalse();
        assertThat(service.isAllowed("a@b@etud.univ-pau.fr")).isFalse();
        verify(repository, never()).existsByDomain(anyString());
    }
}
