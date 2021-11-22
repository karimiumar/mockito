package com.umar.apps.mockito;

import com.umar.apps.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BatchRegistrationTest {

    //Class under test
    private BatchRegistration batchRegistration;

    //Mock
    private CustomerRepository customerRepository;
    private EventRecorder eventRecorder;

    //test data
    private List<Customer> customers;

    @BeforeEach
    void beforeEach() {
        customers = new ArrayList<>();
        eventRecorder = mock(EventRecorder.class);
        customerRepository = mock(CustomerRepository.class);

        when(customerRepository.save(anyString(), anyString()))
                .then((Answer<Customer>) invocationOnMock -> {
                    String fname = invocationOnMock.getArgument(0);
                    String lname = invocationOnMock.getArgument(1);
                    return new Customer(fname, lname, LocalDateTime.now());
                });

        batchRegistration = new BatchRegistration(eventRecorder, customerRepository);
    }

    @Test
    void register10Customers() {
        customers.add(new Customer("Tim", "Berners", LocalDateTime.now()));
        customers.add(new Customer("Tim", "Lee", LocalDateTime.now()));
        customers.add(new Customer("Count", "Berenger", LocalDateTime.now()));
        customers.add(new Customer("Jim", "Tiago", LocalDateTime.now()));
        customers.add(new Customer("Tim", "Quixote", LocalDateTime.now()));
        customers.add(new Customer("Don", "Berners", LocalDateTime.now()));
        customers.add(new Customer("Don", "Cossack", LocalDateTime.now()));
        customers.add(new Customer("Don", "John", LocalDateTime.now()));
        customers.add(new Customer("Don", "Quixote", LocalDateTime.now()));
        customers.add(new Customer("Stephen", "Murray", LocalDateTime.now()));

        batchRegistration.batchRegister(customers);

        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRecorder, times(customers.size())).recordEvent(eventArgumentCaptor.capture());

        List<Event> eventsSent = eventArgumentCaptor.getAllValues();

        assertThat(customers).size().isEqualTo(eventsSent.size());
        AtomicInteger i = new AtomicInteger(0);
        eventsSent.forEach(event -> {
            assertThat(event).isNotNull();
            assertThat(customers.get(i.getAndIncrement()).fullName()).isEqualTo(event.customer());
        });
    }
}
