package com.umar.apps;

import java.time.LocalDateTime;
import java.util.Objects;

public record Customer(String firstName, String lastName, LocalDateTime customerSince) {

    public Customer {
        Objects.requireNonNull(firstName, "firstName is required");
        Objects.requireNonNull(lastName, "lastName is required");
        Objects.requireNonNull(customerSince, "customerSince is required");
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}
