package com.hmarques.webfluxcourse.service;

import static java.lang.String.format;

import com.hmarques.webfluxcourse.entity.User;
import com.hmarques.webfluxcourse.mapper.UserMapper;
import com.hmarques.webfluxcourse.model.request.UserRequest;
import com.hmarques.webfluxcourse.repository.UserRepository;
import com.hmarques.webfluxcourse.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final UserMapper mapper;


  public Mono<User> save(final UserRequest request) {
    return repository.save(mapper.toEntity(request));
  }

  public Mono<User> findById(final String id) {
    return heandleNotFound(repository.findById(id), id);
  }

  public Flux<User> findAll() {
    return repository.findAll();
  }

  public Mono<User> update(final String id, final UserRequest request) {
    return findById(id).map(entity -> mapper.toEntity(request, entity))
        .flatMap(repository::save);
  }


  public Mono<User> delete(final String id) {
    return heandleNotFound(repository.findAndRemove(id), id);
  }

  private <T> Mono<T> heandleNotFound(Mono<T> mono, String id){
    return mono.switchIfEmpty(
        Mono.error(new ObjectNotFoundException(format("Not Found. Id %s, Type: %s", id, User.class.getSimpleName()))));

  }
}
