package com.hmarques.webfluxcourse.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.hmarques.webfluxcourse.entity.User;
import com.hmarques.webfluxcourse.mapper.UserMapper;
import com.hmarques.webfluxcourse.model.request.UserRequest;
import com.hmarques.webfluxcourse.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.control.MappingControl.Use;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository repository;

  @Mock
  private UserMapper mapper;

  @InjectMocks
  private UserService service;

  @Test
  void save() {

    UserRequest request = new UserRequest("henrique", "henrique@email.com", "12345");
    User entity = User.builder().build();

    when(mapper.toEntity(any(UserRequest.class))).thenReturn(entity);
    when(repository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

    Mono<User> result = service.save(request);

    StepVerifier.create(result).expectNextMatches(user -> user != null).expectComplete().verify();
    Mockito.verify(repository, times(1)).save(any(User.class));

  }
}