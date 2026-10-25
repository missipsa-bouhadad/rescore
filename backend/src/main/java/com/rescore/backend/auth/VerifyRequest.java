package com.rescore.backend.auth;

import jakarta.validation.constraints.NotBlank;

// Token sent by the front (read from the email link) to confirm the account
public record VerifyRequest(@NotBlank String token) {
}
