package com.umar.apps;

import java.time.LocalDateTime;
import java.util.Objects;

public record Event(LocalDateTime eventTime, String customer) {

    public Event {
        Objects.requireNonNull(eventTime, "eventTime is required");
        Objects.requireNonNull(customer, "customer is required");
    }
}
