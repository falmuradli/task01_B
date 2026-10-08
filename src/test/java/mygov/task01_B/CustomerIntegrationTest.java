package mygov.task01_B;

import mygov.task01_B.data.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)

@AutoConfigureWebTestClient
public class CustomerIntegrationTest {

    private final WebTestClient webTestClient;

    public CustomerIntegrationTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void getAllCustomers_shouldReturnCustomers() {
        webTestClient.get()
                .uri("/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class);
    }

    @Test
    void getCustomerById_shouldReturnCustomer() {
        webTestClient.get()
                .uri("/customers/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class)
                .value(customer ->
                        org.junit.jupiter.api.Assertions.assertEquals(1L, customer.id()));
    }

    @Test
    void getCustomerById_shouldReturnNotFound() {

        Long nonExistingId = 9999L;

        webTestClient.get()
                .uri("/customers/{id}", nonExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createCustomer_shouldReturnCreated() {

        Customer customer = new Customer(
                20L,
                "New User",
                "newuser@example.com",
                "(010) 222-33-44"
        );

        webTestClient.post()
                .uri("/customers")
                .bodyValue(customer)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Customer.class)
                .isEqualTo(customer);
    }
}
