package com.petal.pokemon.service;

import com.petal.pokemon.model.PaginatedResult;
import com.petal.pokemon.model.PokemonReadDto;
import com.petal.pokemon.model.PokemonWriteDto;

public interface PokemonService {
  PokemonReadDto create(PokemonWriteDto newPokemon);

  void delete(long pokemonId);

  PokemonReadDto findById(long pokemonId);

  PaginatedResult findPaginated(Integer page, Integer perPage);

  PokemonReadDto update(Long id, PokemonWriteDto pokemonDto);
}
