package com.umar.apps;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MockitoTest {

    @Test
    void when_Mockito$anyList_then_NotAMockException() {
        var mockList = Mockito.anyList();
        assertThatThrownBy(() -> Mockito.verify(mockList))
                .hasMessageContaining("Argument passed to verify() is of type ArrayList and is not a mock!");
    }

    @Test
    void when_add_not_invoked_on_mockList_then_verify_fails() {
        var mockList = Mockito.mock(ArrayList.class);
        assertThatThrownBy(() -> Mockito.verify(mockList).add("one"))
                .hasMessageContaining("Actually, there were zero interactions with this mock.");
    }

    @Test
    void when_add_invoked_on_mockList_then_verify_success() {
        var mockList = Mockito.mock(ArrayList.class);
        mockList.add("one");
        Mockito.verify(mockList).add("one");
        assertThat(mockList.size()).isEqualTo(0);
    }

    @Test
    void when_Mockito$when$then_invoked_on_mockList_then_verify_success() {
        var mockList = Mockito.mock(ArrayList.class);
        Mockito.when(mockList.size()).thenReturn(100);
        assertThat(mockList.size()).isEqualTo(100);
    }
}
