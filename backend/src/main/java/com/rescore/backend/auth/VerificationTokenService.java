package com.rescore.backend.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rescore.backend.user.User;

// Create the email link tokens (only the hash is saved on db) and check them on click
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

    // Check the token of the link and activate the account
    @Transactional
    public void confirm(String token) {
        EmailVerificationToken saved = tokens.findByTokenHash(hash(token)).orElseThrow(() -> badRequest("Invalid link"));

        if (saved.isUsed()) {
            throw badRequest("Link already used");
        }
        if (saved.isExpired()) {
            throw badRequest("Link expired");
        }

        saved.markUsed();
        saved.getUser().activate();
    }

    private static ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
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
