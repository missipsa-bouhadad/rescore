package com.rescore.backend.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Rules checked by @Valid in the controller (AuthController)
public record RegisterRequest(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Email @Size(max = 255) @Pattern(regexp = "[^A-Z\\s]+", message = "must be lowercase, without spaces") String email,
        @NotBlank @Size(min = 8, max = 72) String password,
        @NotNull Long residenceId) {
}
