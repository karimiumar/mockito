package com.umar.apps;

import java.time.LocalDateTime;
import java.util.List;

public class BatchRegistration {

    private final EventRecorder eventRecorder;
    private final CustomerRepository customerRepository;

    public BatchRegistration(EventRecorder eventRecorder, CustomerRepository customerRepository) {
        this.eventRecorder = eventRecorder;
        this.customerRepository = customerRepository;
    }

    public void batchRegister(List<Customer> customers) {
        customers.forEach(this::register);
    }

    private void register(Customer customer) {
        var newCustomer = customerRepository.save(customer.firstName(), customer.lastName());
        var event = new Event(LocalDateTime.now(), newCustomer.fullName());
        eventRecorder.recordEvent(event);
    }
}
