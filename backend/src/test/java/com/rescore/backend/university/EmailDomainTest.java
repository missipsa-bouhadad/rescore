package com.rescore.backend.university;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

// Tests that we get the right domain from an email
class EmailDomainTest {

    @Test
    void extractsDomain() {
        assertThat(EmailDomain.extract("jean@etud.univ-pau.fr")).isEqualTo("etud.univ-pau.fr");
    }

    @Test
    void lowercasesAndTrims() {
        assertThat(EmailDomain.extract("  Jean@ETUD.Univ-Pau.fr ")).isEqualTo("etud.univ-pau.fr");
    }

    @Test
    void rejectsInvalidEmails() {
        assertThatThrownBy(() -> EmailDomain.extract(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> EmailDomain.extract("jean")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> EmailDomain.extract("a@b@etud.univ-pau.fr")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> EmailDomain.extract("@etud.univ-pau.fr")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> EmailDomain.extract("jean@")).isInstanceOf(IllegalArgumentException.class);
    }
}
