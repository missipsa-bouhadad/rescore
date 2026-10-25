package com.rescore.backend.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.rescore.backend.user.User;

// Tests the creation of confirmation tokens
class VerificationTokenServiceTest {

    private final EmailVerificationTokenRepository tokens = mock(EmailVerificationTokenRepository.class);
    private final VerificationTokenService service = new VerificationTokenService(tokens);

    @Test
    void savesOnlyTheHash() {
        String token = service.createFor(mock(User.class));

        // Catch the object given to save()
        ArgumentCaptor<EmailVerificationToken> saved = ArgumentCaptor.forClass(EmailVerificationToken.class);
        verify(tokens).save(saved.capture());

        assertThat(saved.getValue().getTokenHash()).isEqualTo(VerificationTokenService.hash(token));
        assertThat(saved.getValue().getExpiresAt()).isAfter(Instant.now().plusSeconds(23 * 3600));
    }

    // The token can be put in a link (only letters, digits, - and _)
    @Test
    void createsTokenForALink() {
        String token = service.createFor(mock(User.class));

        assertThat(token).matches("[A-Za-z0-9_-]{43}");
    }

    // A valid link activates the account and cannot be used again
    @Test
    void confirmActivatesAccount() {
        User user = mock(User.class);
        EmailVerificationToken saved = savedToken(user, Instant.now().plusSeconds(3600));

        service.confirm("abc");

        verify(user).activate();
        assertThat(saved.isUsed()).isTrue();
    }

    @Test
    void confirmRejectsUnknownToken() {
        assertThatThrownBy(() -> service.confirm("abc")).hasMessageContaining("Invalid link");
    }

    @Test
    void confirmRejectsUsedToken() {
        savedToken(mock(User.class), Instant.now().plusSeconds(3600)).markUsed();

        assertThatThrownBy(() -> service.confirm("abc")).hasMessageContaining("Link already used");
    }

    @Test
    void confirmRejectsExpiredToken() {
        savedToken(mock(User.class), Instant.now().minusSeconds(60));

        assertThatThrownBy(() -> service.confirm("abc")).hasMessageContaining("Link expired");
    }

    // Fake token "abc" found in the database
    private EmailVerificationToken savedToken(User user, Instant expiresAt) {
        EmailVerificationToken saved = new EmailVerificationToken(user, VerificationTokenService.hash("abc"), expiresAt);
        when(tokens.findByTokenHash(VerificationTokenService.hash("abc"))).thenReturn(Optional.of(saved));
        return saved;
    }
}
