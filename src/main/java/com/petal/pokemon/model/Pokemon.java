package com.petal.pokemon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Pokemon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer attack;
  private Integer defense;

  @Min(1)
  @Max(6)
  private Integer generation; // between 1 and 6

  private Integer hp;
  private boolean legendary;

  @Column(unique = true)
  private String name;

  private Integer spAtk;
  private Integer spDef;
  private Integer speed;

  @Column(nullable = true)
  @Enumerated(EnumType.STRING)
  @NotNull
  private PokemonType type1;

  @Enumerated(EnumType.STRING)
  private PokemonType type2;

  /**
   * The total is the sum of attack + defense + spAtk + spDef + speed
   *
   * @return int: sum of attack + defense + spAtk + spDef + speed
   */
  public Integer getTotal() {
    return hp + attack + defense + spAtk + spDef + speed;
  }
}
