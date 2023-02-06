package com.petal.pokemon.mapper;

import com.petal.pokemon.model.Pokemon;
import com.petal.pokemon.model.PokemonReadDto;
import com.petal.pokemon.model.PokemonWriteDto;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PokemonMapper {

  PokemonReadDto pokemonToPokemonReadDto(Pokemon pokemon);

  List<PokemonReadDto> pokemonListToPokemonReadDtoList(List<Pokemon> pokemon);

  @InheritInverseConfiguration
  Pokemon pokemonWriteDtoToPokemon(PokemonWriteDto pokemonDto);
}
