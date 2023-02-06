package com.petal.pokemon.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = PokemonBadRequestException.class)
  public final ResponseEntity<ApiException> handleBadRequestException(
      PokemonBadRequestException ex) {
    return buildResponseEntity(
        ApiException.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).build());
  }

  @ExceptionHandler(value = PokemonNotFoundException.class)
  public final ResponseEntity<ApiException> handleBadRequestException(PokemonNotFoundException ex) {
    return buildResponseEntity(
        ApiException.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).build());
  }

  private ResponseEntity<ApiException> buildResponseEntity(ApiException apiException) {
    return new ResponseEntity<>(apiException, apiException.getStatus());
  }
}
