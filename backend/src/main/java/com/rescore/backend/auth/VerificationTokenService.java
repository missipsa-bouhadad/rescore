package com.rescore.backend.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

import org.springframework.stereotype.Service;

import com.rescore.backend.user.User;

// Create a random token for the email link, hash it and save only the hash on db
@Service
public class VerificationTokenService {

    static final Duration VALIDITY = Duration.ofHours(24);

    private final SecureRandom random = new SecureRandom();
    private final EmailVerificationTokenRepository tokens;

    public VerificationTokenService(EmailVerificationTokenRepository tokens) {
        this.tokens = tokens;
    }

    // Save the hash and return the token to put in the email
    public String createFor(User user) {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        tokens.save(new EmailVerificationToken(user, hash(token), Instant.now().plus(VALIDITY)));
        return token;
    }

    // SHA-256 of the token, in hexadecimal (64 characters)
    static String hash(String token) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
