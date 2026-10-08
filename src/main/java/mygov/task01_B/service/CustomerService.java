package mygov.task01_B.service;

import mygov.task01_B.data.Customer;
import mygov.task01_B.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<Customer> getAllCustomers() {
        return Flux.fromIterable(customerRepository.findAll());
    }

    public Mono<Customer> getCustomerById(Long id) {
        return Mono.justOrEmpty(customerRepository.findById(id));
    }

    public Mono<Customer> saveCustomer(Customer customer) {
        return Mono.just(customerRepository.save(customer));
    }

}
