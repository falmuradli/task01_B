package mygov.task01_B;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Repository
public class CustomerRepository {

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();

    public Customer save(Customer customer) {
        customers.put(customer.id(), customer);
        return customer;
    }

    public Collection<Customer> findAll() {
        return customers.values();
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customers.get(id));
    }
}
