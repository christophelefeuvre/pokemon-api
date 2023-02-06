package com.petal.pokemon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PokemonBadRequestException extends RuntimeException {
  public PokemonBadRequestException(String message) {
    super(message);
  }
}
