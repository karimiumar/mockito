package com.umar.apps.mockito;

import com.umar.apps.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.io.File;
import java.io.FileFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Mockito.*;

public class ArgumentMatcherTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void before() {
        passwordEncoder = mock(PasswordEncoder.class);
    }

    /*
     * Sometimes we just don’t care about the actual value being passed as an argument,
     * or maybe we want to define a reaction for a wider range of values.
     * All these scenarios (and more) can be addressed with argument matchers.
     * The idea is simple: Instead of providing an exact value, you provide an argument
     * matcher for Mockito to match method arguments against.
     */
    @Test
    void given_passwordEncoder_when_any_String_encode_then_returns_exact() {
        when(passwordEncoder.encode(anyString())).thenReturn("exact");
        assertThat(passwordEncoder.encode("1")).isEqualTo("exact");
    }

    /*
     * Mockito requires you to provide all arguments either by matchers or by exact values.
     * So if a method has more than one argument and you want to use argument matchers for
     * only some of its arguments, forget it. You can’t.
     */
    @Test
    void given_method_call_when_matcher_for_some_args_then_throws_InvalidUseOfMatchersException() {
        abstract class Abstract{
            abstract boolean call(String str, int it);
        }

        Abstract mock = mock(Abstract.class);
        assertThatThrownBy(() -> when(mock.call("A", anyInt())).thenReturn(true));
    }

    @Test
    void given_method_call_when_matcher_for_all_args_then_success() {
        abstract class Abstract {
            abstract boolean call(String str, int it);
        }

        Abstract mock = mock(Abstract.class);
        //see the matcher eq() for String type
        when(mock.call(eq("a"), anyInt())).thenReturn(true);
        assertThat(mock.call("a", 123)).isTrue();
        assertThat(mock.call("abc", 123)).isFalse();
    }

    /*
     * Custom matchers come to the rescue when you need to provide some matching logic
     * that is not already available in Mockito. The decision to create a custom matcher
     * shouldn’t be taken lightly since the need to match arguments in a non-trivial way
     * indicates either a problem in design or that a test is getting too complicated.
     */
    @Test
    void given_custom_matcher_when_applied_then_success() {
        FileFilter fileFilter = mock(FileFilter.class);
        ArgumentMatcher<File> hasLuck = file -> file.getName().endsWith("luck");

        when(fileFilter.accept(argThat(hasLuck))).thenReturn(true);
        assertThat(fileFilter.accept(new File("/deserve"))).isFalse();
        assertThat(fileFilter.accept(new File("/deserve/luck"))).isTrue();
    }

    @Test
    void combinedMatchersTest() {
        when(passwordEncoder.encode(or(eq("1"), contains("a")))).thenReturn("ok");
        assertThat(passwordEncoder.encode("1")).isEqualTo("ok");
        assertThat(passwordEncoder.encode("12a1")).isEqualTo("ok");
        assertThat(passwordEncoder.encode("bbz")).isNull();
    }
}
