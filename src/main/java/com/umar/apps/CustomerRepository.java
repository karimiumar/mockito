package com.umar.apps;

public interface CustomerRepository {
    Customer save(String firstName, String lastName);
}
