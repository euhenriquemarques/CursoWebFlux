package com.hmarques.webfluxcourse.controller.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.hmarques.webfluxcourse.entity.User;
import com.hmarques.webfluxcourse.mapper.UserMapper;
import com.hmarques.webfluxcourse.model.request.UserRequest;
import com.hmarques.webfluxcourse.service.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private UserService service;

  @MockBean
  private UserMapper mapper;

  @MockBean
  private MongoClient mongoClient;

  @Test
  void save() {
    UserRequest userRequest = new UserRequest("henrique", "henrique@henrique.com", "123");

    Mockito.when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));
    webTestClient.post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(userRequest))
        .exchange()
        .expectStatus()
        .isCreated();

    Mockito.verify(service, Mockito.times(1)).save(any(UserRequest.class));

  }


  @Test
  void saveBadRequest() {
    UserRequest userRequest = new UserRequest(" henrique", "henrique@henrique.com", "123");

    webTestClient.post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(userRequest))
        .exchange()
        .expectStatus()
        .isBadRequest().expectBody()
        .jsonPath("$.path").isEqualTo("/users")
        .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
        .jsonPath("$.error").isEqualTo("Bad Request")
        .jsonPath("$.message").isEqualTo("Validation Error on validation atibutes")
        .jsonPath("$.errors[0].fieldName").isEqualTo("name")
        .jsonPath("$.errors[0].message").isEqualTo("Não é aceito espaços em brancos!");
  }

  @Test
  void findById() {
  }

  @Test
  void findAll() {
  }

  @Test
  void update() {
  }

  @Test
  void delete() {
  }
}