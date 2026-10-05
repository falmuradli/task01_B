package mygov.task01_B;

import mygov.task01_B.service.CustomerService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CustomerHandler {

    private final CustomerService customerService;

    public CustomerHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Mono<ServerResponse> getAllCustomers(ServerRequest serverRequest) {
        Flux<Customer> customers = customerService.getAllCustomers();
        return ServerResponse.ok()
                .body(customers, Customer.class);
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        return customerService.getCustomerById(id)
                .flatMap(customer -> ServerResponse.ok().bodyValue(customer))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createCustomer(ServerRequest serverRequest) {
        Mono<Customer> customerMono = serverRequest.bodyToMono(Customer.class);
        return customerMono
                .flatMap(customerService::saveCustomer)
                .flatMap(savedCustomer ->
                        ServerResponse.created(URI.create("/customers/" + savedCustomer.id()))
                                .bodyValue(savedCustomer)
                );
    }
}
