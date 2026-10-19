package com.rescore.backend.auth;

import com.rescore.backend.user.User;
import com.rescore.backend.user.UserStatus;

// Created account sent to the front
public record RegisterResponse(Long id, String email, UserStatus status) {

    public static RegisterResponse from(User user) {
        return new RegisterResponse(user.getId(), user.getEmail(), user.getStatus());
    }
}
