package com.umar.apps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MockInjectionTest {

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    List<String> mockList;

    @Test
    void when_add_not_invoked_on_mockList_then_verify_fails() {
        assertThatThrownBy(() -> Mockito.verify(mockList).add("one"))
                .hasMessageContaining("Actually, there were zero interactions with this mock.");
    }

    @Test
    void when_add_invoked_on_mockList_then_verify_success() {
        mockList.add("one");
        Mockito.verify(mockList).add("one");
        assertThat(mockList.size()).isEqualTo(0);
    }

    @Test
    void when_Mockito$when$then_invoked_on_mockList_then_verify_success() {
        Mockito.when(mockList.size()).thenReturn(100);
        assertThat(mockList.size()).isEqualTo(100);
    }
}
