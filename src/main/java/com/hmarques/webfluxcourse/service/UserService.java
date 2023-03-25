package com.hmarques.webfluxcourse.service;

import com.hmarques.webfluxcourse.entity.User;
import com.hmarques.webfluxcourse.mapper.UserMapper;
import com.hmarques.webfluxcourse.model.request.UserRequest;
import com.hmarques.webfluxcourse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final UserMapper mapper;


  public Mono<User> save(final UserRequest request) {

    return repository.save(mapper.toEntity(request));

  }
}
