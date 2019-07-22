package com.webflux.app.business.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RouterConfigTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  @DisplayName("Should return GET Service")
  void getTest() {
    webTestClient
            .get()
            .uri("/person_list")
            .accept(APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus()
            .isOk();
    webTestClient
            .get()
            .uri("/person_list/tests")
            .accept(APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  @DisplayName("Should return POST Service")
  void postTest() {
    webTestClient
            .post()
            .uri("/person")
            .accept(APPLICATION_JSON_UTF8)
            .contentType(APPLICATION_JSON_UTF8)
            .syncBody("{\"nickname\":\"mandrova\",\"email\":\"mandrova@gmail.com\"}")
            .exchange()
            .expectStatus()
            .is2xxSuccessful();
    webTestClient
            .post()
            .uri("/person")
            .accept(APPLICATION_JSON_UTF8)
            .contentType(APPLICATION_JSON_UTF8)
            .syncBody("{\"nickname\":\"mandrova\",\"email\":\"mandrova@gmail.com\"}")
            .exchange()
            .expectStatus()
            .isBadRequest();
    webTestClient
            .post()
            .uri("/person_test")
            .accept(APPLICATION_JSON_UTF8)
            .contentType(APPLICATION_JSON_UTF8)
            .syncBody("{\"nickname\":\"mandrova\",\"email\":\"mandrova@gmail.com\"}")
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  @DisplayName("Should return PUT Service")
  void putTest() {
    webTestClient
            .put()
            .uri("/person/1")
            .accept(APPLICATION_JSON_UTF8)
            .contentType(APPLICATION_JSON_UTF8)
            .syncBody("{\"nickname\":\"mandrova\"}")
            .exchange()
            .expectStatus()
            .is2xxSuccessful();
    webTestClient
            .put()
            .uri("/person/11")
            .accept(APPLICATION_JSON_UTF8)
            .contentType(APPLICATION_JSON_UTF8)
            .syncBody("{\"nickname\":\"mandrova\"}")
            .exchange()
            .expectStatus()
            .isBadRequest();
    webTestClient
            .put()
            .uri("/person_test/1")
            .accept(APPLICATION_JSON_UTF8)
            .contentType(APPLICATION_JSON_UTF8)
            .syncBody("{\"nickname\":\"mandrova\"}")
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  @DisplayName("Should return DELETE Service")
  void deleteTest() {
    webTestClient
            .delete()
            .uri("/person/1")
            .accept(APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus()
            .is2xxSuccessful();
    webTestClient
            .delete()
            .uri("/person/11")
            .accept(APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus()
            .isBadRequest();
    webTestClient
            .delete()
            .uri("/person_test/11")
            .accept(APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus()
            .isNotFound();
  }


}
