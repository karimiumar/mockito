package com.umar.apps.mockito.di;

import com.umar.apps.PasswordEncoder;
import com.umar.apps.UserRepository;
import com.umar.apps.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.util.MockUtil.isMock;

public class DependencyInjectionTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_passwordEncoder$encode_then_encodes_password() {
        passwordEncoder.encode("1");
        verify(passwordEncoder).encode(anyString());
    }

    @Test
    void verify_zero_interactions() {
        verifyNoMoreInteractions(passwordEncoder, userRepository);
        assertThat(isMock(userService)).isFalse();
    }
}
