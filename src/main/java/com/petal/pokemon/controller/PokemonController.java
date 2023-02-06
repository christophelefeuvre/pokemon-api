package com.petal.pokemon.controller;

import com.petal.pokemon.PokemonsApi;
import com.petal.pokemon.model.PaginatedResult;
import com.petal.pokemon.model.PokemonReadDto;
import com.petal.pokemon.model.PokemonWriteDto;
import com.petal.pokemon.service.PokemonService;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Slf4j
public class PokemonController implements PokemonsApi {

  private PokemonService pokemonService;

  @Override
  public ResponseEntity<PokemonReadDto> addPokemon(PokemonWriteDto pokemonWriteDto) {
    PokemonReadDto pokemonDto = pokemonService.create(pokemonWriteDto);
    return ResponseEntity.created(URI.create("/pokemon/" + pokemonDto.getId())).body(pokemonDto);
  }

  @Override
  public ResponseEntity<Void> deletePokemonById(Long id) {
    pokemonService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Return all pokemons with pagination
   *
   * @param page (optional) : page number
   * @param perPage (optional) : item(s) by page
   * @return {@link PaginatedResult} of pokemons
   */
  @Override
  public ResponseEntity<PaginatedResult> getPokemons(Integer page, Integer perPage) {
    return ResponseEntity.ok(pokemonService.findPaginated(page, perPage));
  }

  @Override
  public ResponseEntity<PokemonReadDto> getPokemonById(Long id) {
    PokemonReadDto pokemonDto = pokemonService.findById(id);
    return ResponseEntity.ok(pokemonDto);
  }

  @Override
  public ResponseEntity<PokemonReadDto> updatePokemon(Long id, PokemonWriteDto pokemonWriteDto) {
    PokemonReadDto pokemonDto = pokemonService.update(id, pokemonWriteDto);
    return ResponseEntity.ok(pokemonDto);
  }
}
