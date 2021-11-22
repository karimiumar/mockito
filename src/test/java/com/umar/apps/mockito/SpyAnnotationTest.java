package com.umar.apps.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * The @Spy annotation is used to create a real object and spy on that real object.
 */
public class SpyAnnotationTest {

    @Spy
    private final LinkedList<String> spyList = new LinkedList<>();

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldAddItemsToListSuccessfully() {
        spyList.add("one");
        spyList.add("two");

        verify(spyList, times(2)).add(anyString());

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertThat(spyList).size().isEqualTo(2);
    }

    @Test
    void when_size_stubbedWith100_then_size_returns_100() {
        spyList.add("One");
        spyList.add("Two");

        verify(spyList).add("One");
        verify(spyList).add("Two");

        assertThat(spyList).size().isEqualTo(2);

        doReturn(100).when(spyList).size();

        assertThat(spyList).size().isEqualTo(100);
    }
}
