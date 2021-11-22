package com.umar.apps.mockito;

import com.umar.apps.PasswordEncoder;
import com.umar.apps.User;
import com.umar.apps.UserRepository;
import com.umar.apps.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private static final String PASSWORD = "password";
    private static final User ENABLED_USER = new User("user id", "hash", true);
    private static final User DISABLED_USER = new User("disabled id", "disabled hash", false);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void before() {
        userRepository = createUserRepository();
        passwordEncoder = createPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void given_userService_when_valid_credentials_then_success() {
        var isValidUser = userService.isValidUser(ENABLED_USER.id(), PASSWORD);
        assertThat(isValidUser).isTrue();
        verify(userRepository).findById(ENABLED_USER.id());
        verify(passwordEncoder).encode(PASSWORD);
    }

    @Test
    void given_userService_when_invalid_credentials_then_invalid() {
        boolean isValidUser = userService.isValidUser(DISABLED_USER.id(), PASSWORD);
        assertThat(isValidUser).isFalse();
        var inOrder = inOrder(userRepository, passwordEncoder);
        inOrder.verify(userRepository).findById(DISABLED_USER.id());
        inOrder.verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void given_userService_when_disabledUser_then_invalid() {
        boolean isValidUser = userService.isValidUser(DISABLED_USER.id(), PASSWORD);
        assertThat(isValidUser).isFalse();
        verify(userRepository).findById(DISABLED_USER.id());
        verifyNoInteractions(passwordEncoder);
    }

    private UserRepository createUserRepository() {
        var repository = mock(UserRepository.class);
        when(repository.findById(ENABLED_USER.id())).thenReturn(ENABLED_USER);
        when(repository.findById(DISABLED_USER.id())).thenReturn(DISABLED_USER);
        return repository;
    }


    private PasswordEncoder createPasswordEncoder() {
        var encoder = mock(PasswordEncoder.class);
        when(encoder.encode(anyString())).thenReturn("any password hash");
        when(encoder.encode(PASSWORD)).thenReturn(ENABLED_USER.passwordHash());
        return encoder;
    }
}
