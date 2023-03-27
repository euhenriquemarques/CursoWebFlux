package com.hmarques.webfluxcourse.service;

import static java.lang.String.format;

import com.hmarques.webfluxcourse.entity.User;
import com.hmarques.webfluxcourse.mapper.UserMapper;
import com.hmarques.webfluxcourse.model.request.UserRequest;
import com.hmarques.webfluxcourse.repository.UserRepository;
import com.hmarques.webfluxcourse.service.exception.ObjectNotFoundException;
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

  public Mono<User> findById(final String id){
    return repository.findById(id).switchIfEmpty(Mono.error(
        new ObjectNotFoundException(format("Not Found. Id %s, Type: %s", id, User.class.getSimpleName() ))
    ));
  }
}
