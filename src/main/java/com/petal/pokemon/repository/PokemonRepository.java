package com.petal.pokemon.repository;

import com.petal.pokemon.model.Pokemon;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
  Optional<Pokemon> findByName(String name);

  Page<Pokemon> findAll(Pageable pageable);
}
