package com.hmarques.webfluxcourse.model.response;

public record UserResponse(
    String id,
    String nome,
    String email,
    String password
) {

}
