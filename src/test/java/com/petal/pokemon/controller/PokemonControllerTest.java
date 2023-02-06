package com.petal.pokemon.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petal.pokemon.PokemonApplication;
import com.petal.pokemon.model.PokemonReadDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(classes = PokemonApplication.class)
@AutoConfigureMockMvc
public class PokemonControllerTest {
  @Autowired MockMvc mockMvc;

  @Test
  public void getPokemonById() throws Exception {
    MvcResult result =
        mockMvc.perform(get("/pokemons/1").contentType(MediaType.APPLICATION_JSON)).andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void addNewPokemon() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                post("/pokemons")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\n"
                            + "  \"attack\": 10,\n"
                            + "  \"defense\": 10,\n"
                            + "  \"generation\": 1,\n"
                            + "  \"hp\": 55,\n"
                            + "  \"legendary\": true,\n"
                            + "  \"name\": \"Chris\",\n"
                            + "  \"spAtk\": 50,\n"
                            + "  \"spDef\": 20,\n"
                            + "  \"speed\": 10,\n"
                            + "  \"type1\": \"BUG\",\n"
                            + "  \"type2\": \"BUG\"\n"
                            + "}"))
            .andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
  }

  @Test
  public void addPokemonWithSameName() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                post("/pokemons")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\n"
                            + "  \"attack\": 10,\n"
                            + "  \"defense\": 10,\n"
                            + "  \"generation\": 1,\n"
                            + "  \"hp\": 55,\n"
                            + "  \"legendary\": true,\n"
                            + "  \"name\": \"Bulbasaur\",\n"
                            + "  \"spAtk\": 50,\n"
                            + "  \"spDef\": 20,\n"
                            + "  \"speed\": 10,\n"
                            + "  \"type1\": \"BUG\",\n"
                            + "  \"type2\": \"BUG\"\n"
                            + "}"))
            .andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(result.getResolvedException().getMessage())
        .isEqualTo("Pokemon name Bulbasaur has already been used");
  }

  @Test
  public void addNewPokemonAndDeleteIt() throws Exception {
    MvcResult resultCreate =
        mockMvc
            .perform(
                post("/pokemons")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\n"
                            + "  \"attack\": 10,\n"
                            + "  \"defense\": 10,\n"
                            + "  \"generation\": 1,\n"
                            + "  \"hp\": 55,\n"
                            + "  \"legendary\": true,\n"
                            + "  \"name\": \"Chris\",\n"
                            + "  \"spAtk\": 50,\n"
                            + "  \"spDef\": 20,\n"
                            + "  \"speed\": 10,\n"
                            + "  \"type1\": \"BUG\",\n"
                            + "  \"type2\": \"BUG\"\n"
                            + "}"))
            .andReturn();

    PokemonReadDto pokemonCreated =
        new ObjectMapper()
            .readValue(resultCreate.getResponse().getContentAsString(), PokemonReadDto.class);

    MvcResult result =
        mockMvc
            .perform(
                delete("/pokemons/" + pokemonCreated.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
  }
}
