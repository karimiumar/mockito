package com.umar.apps;

import java.util.Objects;

public record User(String id, String passwordHash, boolean enabled) {

    public User {
        Objects.requireNonNull(id, "id is required");
        Objects.requireNonNull(passwordHash, "passwordHash is required");
    }
}
