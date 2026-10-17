package com.rescore.backend.university;

import java.util.Locale;

// Extracts domain part of an email address
public final class EmailDomain {

    private EmailDomain() {}

    public static String extract(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email is required");
        }

        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);

        String[] parts = normalizedEmail.split("@", -1);
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }

        return parts[1];
    }
}
