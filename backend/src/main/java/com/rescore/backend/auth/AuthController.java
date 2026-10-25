package com.rescore.backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Endpoints for sign up and login
@Tag(name = "Authentication")
@RestController
public class AuthController {

    private final RegistrationService registrationService;
    private final VerificationTokenService verificationTokenService;

    public AuthController(RegistrationService registrationService, VerificationTokenService verificationTokenService) {
        this.registrationService = registrationService;
        this.verificationTokenService = verificationTokenService;
    }

    @Operation(summary = "Sign up with a student email")
    @ApiResponse(responseCode = "201", description = "Account created with PENDING status")
    @ApiResponse(responseCode = "400", description = "Invalid data, email domain not allowed or wrong residence")
    @ApiResponse(responseCode = "409", description = "Email already used")
    
    @PostMapping("/api/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return registrationService.register(request);
    }

    @Operation(summary = "Confirm the email with the token of the link")
    @ApiResponse(responseCode = "204", description = "Account is now ACTIVE")
    @ApiResponse(responseCode = "400", description = "Invalid, expired or already used link")

    @PostMapping("/api/auth/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204: success (nothing to send back)
    public void verify(@Valid @RequestBody VerifyRequest request) {
        verificationTokenService.confirm(request.token());
    }
}
