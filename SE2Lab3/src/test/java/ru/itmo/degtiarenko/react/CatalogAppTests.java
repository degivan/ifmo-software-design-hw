package ru.itmo.degtiarenko.react;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.itmo.degtiarenko.react.model.Product;
import ru.itmo.degtiarenko.react.model.UserInfo;
import ru.itmo.degtiarenko.react.repository.ProductRepository;
import ru.itmo.degtiarenko.react.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatalogAppTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateUser() {
        UserInfo user = new UserInfo();
        user.setId("4");
        user.setCurrency("dollar");

        userRepository.deleteAll().block();

        webTestClient.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(user), UserInfo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.currency").isEqualTo("dollar");
    }

    @Test
    public void testCreateProduct() {
        UserInfo user = new UserInfo();
        user.setId("4");
        user.setCurrency("dollar");

        userRepository.save(user).block();

        Product product = new Product();
        product.setName("test");
        product.setPrice(1.0);

        webTestClient.post().uri("/products?id=4")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath(".name").isNotEmpty()
                .jsonPath("$.price").isNumber();
    }

    @Test
    public void testGetProducts() {
        UserInfo user = new UserInfo();
        user.setId("4");
        user.setCurrency("dollar");

        userRepository.save(user).block();

        Product product = new Product();
        product.setName("test");
        product.setPrice(1.0);

        productRepository.save(product);

        webTestClient.get().uri("/products?id=4")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class);
    }
}
