package com.umar.apps.mockito.di;

import java.util.Objects;

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isValidUser(String id, String password) {
        User user = userRepository.findById(id);
        return isEnabledUser(user) && isValidPassword(user, password);
    }

    private boolean isEnabledUser(User user) {
        Objects.requireNonNull(user, "user cannot be null");
        return user.enabled();
    }

    private boolean isValidPassword(User user, String password) {
        Objects.requireNonNull(user, "user cannot be null");
        Objects.requireNonNull(password, "password cannot be null");
        String encoded = passwordEncoder.encode(password);
        return encoded.equals(user.passwordHash());
    }
}
