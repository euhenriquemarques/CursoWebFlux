package com.hmarques.webfluxcourse.controller.exception;


import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDateTime;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(DuplicateKeyException.class)
  ResponseEntity<Mono<StandardError>> duplicatedKey(DuplicateKeyException ex, ServerHttpRequest request) {

    return ResponseEntity.badRequest().body(Mono.just(
        StandardError.builder().timestamp(LocalDateTime.now()).status(BAD_REQUEST.value())
            .error(BAD_REQUEST.getReasonPhrase()).message(verifyDupKey(ex.getMessage()))
            .path(request.getPath().toString()).build()));

  }

  private String verifyDupKey(String message) {
    if (message.contains("email dup key")) {
      return "E-mail already registered";
    }

    return "Dup key exception";
  }

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<Mono<ValidationError>> validationError(WebExchangeBindException ex, ServerHttpRequest request) {
    ValidationError error = new ValidationError(LocalDateTime.now(), request.getPath().toString(), BAD_REQUEST.value(),
        BAD_REQUEST.getReasonPhrase(), "Validation Error on validation atibutes");

    for (FieldError x : ex.getBindingResult().getFieldErrors()) {
      error.addError(x.getField(), x.getDefaultMessage());
    }

    return ResponseEntity.status(BAD_REQUEST).body(Mono.just(error));

  }

}
