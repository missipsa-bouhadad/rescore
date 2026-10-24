package com.rescore.backend.auth;

// Published when an account is created (to send the confirmation email)
public record UserRegisteredEvent(String email, String firstName, String token) {
}
