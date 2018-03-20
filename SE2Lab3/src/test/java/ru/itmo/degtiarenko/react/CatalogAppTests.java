package ru.itmo.degtiarenko.react;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.itmo.degtiarenko.react.model.UserInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatalogAppTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testCreateUser() {
        UserInfo user = new UserInfo();
        user.setId("4");
        user.setCurrency("dollar");

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
}
