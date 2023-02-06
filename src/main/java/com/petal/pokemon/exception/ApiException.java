package com.petal.pokemon.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@Builder
public class ApiException {
  private HttpStatus status;
  private String message;
}
