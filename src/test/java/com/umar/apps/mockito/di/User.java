package com.umar.apps.mockito.di;

import java.util.Objects;

public record User(String id, String passwordHash, boolean enabled) {

    public User {
        Objects.requireNonNull(id, "id is required");
        Objects.requireNonNull(passwordHash, "passwordHash is required");
    }
}
