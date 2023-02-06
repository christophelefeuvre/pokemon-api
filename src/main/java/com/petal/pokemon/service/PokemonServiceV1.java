package com.petal.pokemon.service;

import com.petal.pokemon.exception.PokemonBadRequestException;
import com.petal.pokemon.exception.PokemonNotFoundException;
import com.petal.pokemon.mapper.PokemonMapper;
import com.petal.pokemon.model.PaginatedResult;
import com.petal.pokemon.model.Pokemon;
import com.petal.pokemon.model.PokemonReadDto;
import com.petal.pokemon.model.PokemonWriteDto;
import com.petal.pokemon.repository.PokemonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class PokemonServiceV1 implements PokemonService {

  private final PokemonMapper pokemonMapper;
  private final PokemonRepository pokemonRepository;

  @Override
  public PokemonReadDto create(PokemonWriteDto newPokemon) {
    log.info("[create] Creating pokemon {}", newPokemon.getName());
    checkNameIsUnique(newPokemon);
    return saveAndGetPokemon(newPokemon);
  }

  private PokemonReadDto saveAndGetPokemon(PokemonWriteDto newPokemon) {
    Pokemon pokemon = pokemonMapper.pokemonWriteDtoToPokemon(newPokemon);
    try {
      pokemon = pokemonRepository.save(pokemon);
    } catch (Exception exception) {
      log.error("[create] Error in saving pokemon: ", exception);
      throw exception;
    }
    return pokemonMapper.pokemonToPokemonReadDto(pokemon);
  }

  private void checkNameIsUnique(PokemonWriteDto newPokemon) {
    pokemonRepository
        .findByName(newPokemon.getName())
        .ifPresent(
            pokemon -> {
              throw new PokemonBadRequestException(
                  String.format("Pokemon name %s has already been used", newPokemon.getName()));
            });
  }

  @Override
  public void delete(long pokemonId) {
    log.info("[pokemonId:{}][delete] Deleting pokemon", pokemonId);
    pokemonRepository.deleteById(pokemonId);
  }

  @Override
  public PokemonReadDto findById(long pokemonId) throws EntityNotFoundException {
    Pokemon savedPokemon =
        pokemonRepository
            .findById(pokemonId)
            .orElseThrow(
                () ->
                    new PokemonNotFoundException(
                        String.format("Pokemon %s doesn't exist", pokemonId)));
    return pokemonMapper.pokemonToPokemonReadDto(savedPokemon);
  }

  @Override
  public PokemonReadDto update(Long id, PokemonWriteDto pokemonWriteDto) {
    log.info("[pokemonId:{}][update] Updating pokemon", id);
    checkPokemonExists(id);

    Pokemon pokemonEntityFromDto = pokemonMapper.pokemonWriteDtoToPokemon(pokemonWriteDto);
    pokemonEntityFromDto.setId(id);
    pokemonRepository.save(pokemonEntityFromDto);
    return pokemonMapper.pokemonToPokemonReadDto(pokemonEntityFromDto);
  }

  @Override
  public PaginatedResult findPaginated(Integer page, Integer perPage) {
    Page<Pokemon> pageablePokemon = pokemonRepository.findAll(PageRequest.of(page, perPage));
    PaginatedResult paginatedResult = buildingPaginatedResult(pageablePokemon);
    return paginatedResult;
  }

  private PaginatedResult buildingPaginatedResult(Page<Pokemon> pageablePokemon) {
    PaginatedResult paginatedResult = new PaginatedResult();
    paginatedResult.setContent(
        pokemonMapper.pokemonListToPokemonReadDtoList(pageablePokemon.getContent()));
    paginatedResult.setCurrentPage(pageablePokemon.getPageable().getPageNumber());
    paginatedResult.setTotalPages(pageablePokemon.getTotalPages());
    paginatedResult.setTotalItems(pageablePokemon.getTotalElements());
    return paginatedResult;
  }

  private void checkPokemonExists(Long id) {
    pokemonRepository
        .findById(id)
        .orElseThrow(
            () -> {
              throw new PokemonNotFoundException("Pokemon " + id + " doesn't exist");
            });
  }
}
