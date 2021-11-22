package com.umar.apps.mockito;

import com.umar.apps.mockito.di.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *  Besides verifying that a method was called with specific arguments,
 *  Mockito allows you to capture those arguments so that you can
 *  later run custom assertions on them
 *
 */
public class ArgumentCaptorTest {
    //Let’s create a mock of PasswordEncoder, call encode(), capture the argument, and check its value:
    @Test
    void given_PasswordEncoder_when_encode_calls_single_item_then_capture_calls_single_value() {
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        passwordEncoder.encode("password");
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(passwordEncoder, times(1)).encode(passwordCaptor.capture());
        assertThat("password").isEqualTo(passwordCaptor.getValue());
    }

    @Test
    void given_PasswordEncoder_when_encode_called_multiple_times_then_captor_captures_all_values() {
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        passwordEncoder.encode("password1");
        passwordEncoder.encode("password2");
        passwordEncoder.encode("password3");

        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(passwordEncoder, times(3)).encode(passwordCaptor.capture());
        assertThat(passwordCaptor.getAllValues()).containsAll(List.of("password1", "password2", "password3"));
    }
}
