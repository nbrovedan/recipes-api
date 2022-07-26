package com.candidate.naidion.recipes.resource;

import com.candidate.naidion.recipes.config.IntegrationTestConfig;
import com.candidate.naidion.recipes.entity.Recipe;
import com.candidate.naidion.recipes.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecipeResourceTest extends IntegrationTestConfig {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void shouldListAllRecipe() throws Exception {
        mockMvc.perform(get("/recipes")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(2)));
    }

    @Test
    void shouldFilterRecipesByVegetarian() throws Exception {
        mockMvc.perform(get("/recipes?vegetarian=true")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(1)));
    }

    @Test
    void shouldFilterRecipesByPeople() throws Exception {
        mockMvc.perform(get("/recipes?people=5")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(1)));
    }

    @Test
    void shouldFilterRecipesByInstructions() throws Exception {
        mockMvc.perform(get("/recipes?instructions=Cook")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(2)));
    }

    @Test
    void shouldFilterRecipesWithIngredients() throws Exception {
        mockMvc.perform(get("/recipes?withIngredients=Onion,Tomato")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(1)));
    }

    @Test
    void shouldFilterRecipesWithoutIngredients() throws Exception {
        mockMvc.perform(get("/recipes?withoutIngredients=Oil,Chicken")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(1)));
    }

    @Test
    void shouldListNone() throws Exception {
        mockMvc.perform(get("/recipes?withIngredients=Chichek&vegetarian=true")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(0)));
    }

    @Test
    void shouldCreateARecipe() throws Exception {
        var unit = new HashMap<String, Object>();
        unit.put("id", 1);
        unit.put("name","Tablespoon");
        unit.put("initials","tbsp");
        var ingredients = new HashMap<String, Object>();
        ingredients.put("name", "Oil");
        ingredients.put("amount", 150);
        ingredients.put("unit", unit);
        var body = new HashMap<String, Object>();
        body.put("name", "Recipe Mock");
        body.put("instructions", "Cook the mock");
        body.put("people", 3);
        body.put("vegetarian", false);
        body.put("ingredients", List.of(ingredients));
        mockMvc.perform(post("/recipes")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(201));
        var recipes = recipeRepository.findAll();
        Assertions.assertEquals(3, recipes.size());
    }

    @Test
    void shouldNotCreateARecipeWithoutRequiredFields() throws Exception {
        var unit = new HashMap<String, Object>();
        unit.put("id", 1);
        unit.put("name","Tablespoon");
        unit.put("initials","tbsp");
        var ingredients = new HashMap<String, Object>();
        ingredients.put("name", "Oil");
        ingredients.put("amount", 150);
        ingredients.put("unit", unit);
        var body = new HashMap<String, Object>();
        body.put("name", "Recipe Mock");
        body.put("instructions", "Cook the mock");
        body.put("vegetarian", false);
        body.put("ingredients", List.of(ingredients));
        mockMvc.perform(post("/recipes")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(400));
    }

    @Test
    void shouldFindById() throws Exception {
        mockMvc.perform(get("/recipes/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldNotFindById() throws Exception {
        mockMvc.perform(get("/recipes/9999")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateARecipe() throws Exception {
        var body = new HashMap<String, Object>();
        body.put("name", "Mock");
        body.put("instructions", "Instructions mock");
        body.put("people", 99);
        body.put("vegetarian", false);
        mockMvc.perform(put("/recipes/1")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(202));
        var recipe = recipeRepository.findById(1).orElse(Recipe.builder().build());
        Assertions.assertEquals(99, recipe.getPeople());
    }

    @Test
    void shouldNotUpdateARecipe() throws Exception {
        var body = new HashMap<String, Object>();
        body.put("name", "Mock");
        body.put("instructions", "Instructions mock");
        body.put("people", 99);
        body.put("vegetarian", false);
        mockMvc.perform(put("/recipes/9999")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteARecipe() throws Exception {
        mockMvc.perform(delete("/recipes/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(202));
        var recipe = recipeRepository.findById(1).orElse(null);
        Assertions.assertNull(recipe);
    }

    @Test
    void shouldNotDeleteARecipe() throws Exception {
        mockMvc.perform(delete("/recipes/9999")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}