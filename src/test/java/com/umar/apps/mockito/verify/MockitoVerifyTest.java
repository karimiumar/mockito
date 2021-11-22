package com.umar.apps.mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.exceptions.verification.ArgumentsAreDifferent;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.VerificationInOrderFailure;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class MockitoVerifyTest {

    //Given test verifies that the size() method of MyList is invoked on mock
    @Test
    void invocationTest() {
        MyList list = mock(MyList.class);
        list.size(); //invoked size method
        verify(list).size();
    }

    @Test
    void size_invoked_once() {
        MyList list = mock(MyList.class);
        list.size();//invoked size method
        verify(list, times(1)).size();
    }

    @Test
    void size_invoked_thrice() {
        MyList list = mock(MyList.class);
        list.size();//invoked size method once
        list.size();//invoked size method twice
        list.size();//invoked size method thrice
        verify(list, times(3)).size();
    }

    @Test
    void no_method_ever_invoked() {
        MyList list = mock(MyList.class);
        verifyNoInteractions(list);
    }

    @Test
    void size_never_invoked() {
        MyList list = mock(MyList.class);
        verify(list, times(0)).size(); //size never invoked
    }

    @Test
    void given_List_when_methods_Invoked_on_mockList_then_verifyNoMoreInteractions_throws_Exception() {
        MyList mockedList = mock(MyList.class);
        mockedList.add("1");
        mockedList.size();
        mockedList.clear();
        assertThatThrownBy(() -> verifyNoInteractions(mockedList));
    }

    @Test
    void given_List_when_methods_invoked_in_order_then_InOrder_verify_returns_success() {
        MyList mockedList = mock(MyList.class);
        mockedList.add("1");
        mockedList.size();
        mockedList.clear();
        InOrder inOrder = inOrder(mockedList);
        inOrder.verify(mockedList).add(anyString());
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList).clear();
    }

    @Test
    void given_List_when_methods_invoked_not_in_order_then_InOrder_verify_throws_VerificationInOrderFailure(){
        MyList mockedList = mock(MyList.class);
        mockedList.size();
        mockedList.add("1");
        mockedList.clear();
        InOrder inOrder = inOrder(mockedList);
        inOrder.verify(mockedList).add(anyString());
        assertThatThrownBy(() -> inOrder.verify(mockedList).size());
    }

    @Test
    void given_List_when_a_method_never_invoked_then_verify$never_returns_success() {
        MyList mockedList = mock(MyList.class);
        verify(mockedList, never()).size(); //size() method was never invoked on MyList
    }

    @Test
    void given_List_verify_method_was_invoked_certain_no_of_times(){
        MyList mockedList = mock(MyList.class);
        mockedList.size();
        mockedList.size();
        mockedList.size();

        verify(mockedList, atLeast(1)).size();
        verify(mockedList, atMost(10)).size();
    }

    @Test
    void given_List_add_method_called_with_exactly_test_argument(){
        MyList mockedList = mock(MyList.class);
        mockedList.add("test");
        verify(mockedList).add("test");//verified that add() was called with "test" argument
    }

    @Test
    void given_List_when_add_method_called_with_different_argument_then_Exception() {
        MyList mockedList = mock(MyList.class);
        mockedList.add("test");
        assertThatThrownBy(() -> verify(mockedList).add("123"))
                .hasMessageContaining("Argument(s) are different! Wanted:");
    }
}
