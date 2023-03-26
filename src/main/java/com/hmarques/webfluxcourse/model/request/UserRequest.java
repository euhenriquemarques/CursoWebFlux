package com.hmarques.webfluxcourse.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

    @NotBlank(message = "nao pode ser nulo")
    @Size(min = 3, max = 50, message = "O tamanho deve contem entre 3 e 50 caracteres")
    String name,

    @Email(message = "Email invalido")
    @NotBlank(message = "nao pode ser nulo")
    String email,

    @NotBlank(message = "nao pode ser nulo")
    @Size(min = 3, max = 50, message = "O tamanho deve contem entre 3 e 50 caracteres")
    String password

) {

}
