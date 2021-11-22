package com.umar.apps.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AnnotationLessSpyListTest {

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_no_Spy_annotation_then_success() {
        LinkedList<String> list = new LinkedList<>();
        var spyList = Mockito.spy(list);

        spyList.add("One");
        spyList.add("Two");

        verify(spyList).add("One");
        verify(spyList).add("Two");

        assertThat(spyList).size().isEqualTo(2);

        Mockito.doReturn(100).when(spyList).size();

        assertThat(spyList).size().isEqualTo(100);
    }

    @Test
    public void shouldAddItemsToListSuccessfullyUsingMockMethod() {
        LinkedList<String> list = new LinkedList<>();
        var spyList = Mockito.spy(list);

        spyList.add("one");
        spyList.add("two");

        verify(spyList, times(2)).add(anyString());

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertThat(spyList).size().isEqualTo(2);
    }
}
