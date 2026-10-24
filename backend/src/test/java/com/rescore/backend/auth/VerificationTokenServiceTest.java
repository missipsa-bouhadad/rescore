package com.rescore.backend.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.Instant;

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
}
