package com.candidate.naidion.recipes.resource;

import com.candidate.naidion.recipes.config.IntegrationTestConfig;
import com.candidate.naidion.recipes.entity.Ingredient;
import com.candidate.naidion.recipes.repository.IngredientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IngredientsResourceTest extends IntegrationTestConfig {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void shouldListAllIngredientsByRecipe() throws Exception {
        var ingredients = ingredientRepository.findAllByRecipeId(2);
        mockMvc.perform(get("/recipes/2/ingredients")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.hasSize(ingredients.size())));
    }

    @Test
    void shouldNotListAllIngredientsByRecipe() throws Exception {
        mockMvc.perform(get("/recipes/9999/ingredients")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldAddAnIngredientToRecipe() throws Exception {
        var unit = new HashMap<String, Object>();
        unit.put("id", 1);
        unit.put("name","Tablespoon");
        unit.put("initials","tbsp");
        var body = new HashMap<String, Object>();
        body.put("name", "Oil");
        body.put("amount", 150);
        body.put("unit", unit);
        mockMvc.perform(post("/recipes/2/ingredients")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(201));
    }

    @Test
    void shoulNotdAddAnIngredientToRecipe() throws Exception {
        var unit = new HashMap<String, Object>();
        unit.put("id", 1);
        unit.put("name","Tablespoon");
        unit.put("initials","tbsp");
        var body = new HashMap<String, Object>();
        body.put("name", "Oil");
        body.put("unit", unit);
        mockMvc.perform(post("/recipes/1/ingredients")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }

    @Test
    void shouldFindByRecipeAndIngredientId() throws Exception {
        mockMvc.perform(get("/recipes/2/ingredients/5")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldNotFindByRecipeAndIngredientId() throws Exception {
        mockMvc.perform(get("/recipes/1/ingredients/9999")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldRemoveAnIngredient() throws Exception {
        var ingredientsBefore = ingredientRepository.findAllByRecipeId(2);
        mockMvc.perform(delete("/recipes/2/ingredients/6")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(202));
        var ingredientsAfter = ingredientRepository.findAllByRecipeId(2);
        Assertions.assertTrue(ingredientsBefore.size() > ingredientsAfter.size());
    }

    @Test
    void shouldNotRemoveAnIngredient() throws Exception {
        mockMvc.perform(delete("/recipes/1/ingredients/9999")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateAnIngredient() throws Exception {
        var newName = "Vinager";
        var unit = new HashMap<String, Object>();
        unit.put("id", 1);
        unit.put("name","Tablespoon");
        unit.put("initials","tbsp");
        var body = new HashMap<String, Object>();
        body.put("name", newName);
        body.put("amount", 1.90);
        body.put("unit", unit);
        mockMvc.perform(put("/recipes/2/ingredients/5")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(202));
        var ingredient = ingredientRepository.findById(5).orElse(Ingredient.builder().build());
        Assertions.assertEquals(newName, ingredient.getName());
    }

    @Test
    void shouldUpdateAnIngredientWithoutRequiredField() throws Exception {
        var newName = "Vinager";
        var unit = new HashMap<String, Object>();
        unit.put("id", 1);
        unit.put("name","Tablespoon");
        unit.put("initials","tbsp");
        var body = new HashMap<String, Object>();
        body.put("amount", newName);
        body.put("unit", unit);
        mockMvc.perform(put("/recipes/2/ingredients/4")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }

    @Test
    void shouldNotUpdateAnIngredient() throws Exception {
        var newName = "Vinager";
        var unit = new HashMap<String, Object>();
        unit.put("id", 1);
        unit.put("name","Tablespoon");
        unit.put("initials","tbsp");
        var body = new HashMap<String, Object>();
        body.put("name", newName);
        body.put("unit", unit);
        mockMvc.perform(put("/recipes/1/ingredients/9999")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}