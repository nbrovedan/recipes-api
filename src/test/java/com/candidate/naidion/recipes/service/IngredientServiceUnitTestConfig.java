package com.candidate.naidion.recipes.service;

import com.candidate.naidion.recipes.config.UnitTestConfig;
import com.candidate.naidion.recipes.dto.CreateIngredientsDTO;
import com.candidate.naidion.recipes.dto.UnitDTO;
import com.candidate.naidion.recipes.entity.Ingredient;
import com.candidate.naidion.recipes.entity.Unit;
import com.candidate.naidion.recipes.enums.ErrorsEnum;
import com.candidate.naidion.recipes.exception.IngredientException;
import com.candidate.naidion.recipes.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("IngredientServiceTest")
class IngredientServiceUnitTestConfig extends UnitTestConfig {

    @MockBean
    private RecipeService recipeService;
    @MockBean
    private IngredientRepository ingredientRepository;
    @Autowired
    private IngredientService ingredientService;

    @Test
    void shouldAddAnIngredient(){
        var idMock = 1;
        Mockito.doNothing().when(recipeService).addIngredient(anyInt(), any(Ingredient.class));
        var unitMock = mock(UnitDTO.class);
        when(unitMock.getId()).thenReturn(idMock);
        when(unitMock.getName()).thenReturn("Mock");
        when(unitMock.getInitials()).thenReturn("mck");
        var createIngredientMock = mock(CreateIngredientsDTO.class);
        when(createIngredientMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(createIngredientMock.getName()).thenReturn("Mock");
        when(createIngredientMock.getDescription()).thenReturn(null);
        when(createIngredientMock.getUnit()).thenReturn(unitMock);
        ingredientService.addIngredients(idMock, createIngredientMock);
        Mockito.verify(recipeService, Mockito.times(1)).addIngredient(anyInt(), any(Ingredient.class));
    }

    @Test
    void shouldListAllIngredients(){
        var idMock = 1;
        var unitMock = mock(Unit.class);
        when(unitMock.getId()).thenReturn(idMock);
        when(unitMock.getName()).thenReturn("Mock");
        when(unitMock.getInitials()).thenReturn("mck");
        var ingredientMock = mock(Ingredient.class);
        when(ingredientMock.getId()).thenReturn(idMock);
        when(ingredientMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(ingredientMock.getDescription()).thenReturn(null);
        when(ingredientMock.getUnit()).thenReturn(unitMock);
        when(ingredientMock.getName()).thenReturn("Mock");
        var ingredientList = List.of(ingredientMock);
        when(ingredientRepository.findAllByRecipeId(anyInt())).thenReturn(ingredientList);
        var ingredients = ingredientService.findAll(idMock);
        Assertions.assertEquals(ingredientList.size(), ingredients.size());
    }

    @Test
    void shouldThrowsNoIngredientsOnFindAll() {
        var idMock = 1;
        when(ingredientRepository.findAllByRecipeId(anyInt())).thenReturn(Collections.emptyList());
        var exception = Assertions.assertThrows(IngredientException.class, () -> ingredientService.findAll(idMock));
        Assertions.assertEquals(ErrorsEnum.INGREDIENTS_NOT_FOUND_BY_RECIPE_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldFindByRecipeAndIngredientId() {
        var idMock = 1;
        var unitMock = mock(Unit.class);
        when(unitMock.getId()).thenReturn(idMock);
        when(unitMock.getName()).thenReturn("Mock");
        when(unitMock.getInitials()).thenReturn("mck");
        var ingredientMock = mock(Ingredient.class);
        when(ingredientMock.getId()).thenReturn(idMock);
        when(ingredientMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(ingredientMock.getDescription()).thenReturn(null);
        when(ingredientMock.getUnit()).thenReturn(unitMock);
        when(ingredientMock.getName()).thenReturn("Mock");
        when(ingredientRepository.findByRecipeIdAndId(anyInt(), anyInt())).thenReturn(Optional.of(ingredientMock));
        var ingredient = ingredientService.findByRecipeIngredientId(idMock, idMock);
        Assertions.assertEquals(idMock, ingredient.getId());
    }

    @Test
    void shouldThrowsNoIngredientsByRecipeAndIngredientIdOnFindByIds() {
        when(ingredientRepository.findByRecipeIdAndId(anyInt(), anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(IngredientException.class, () -> ingredientService.findByRecipeIngredientId(anyInt(), anyInt()));
        Assertions.assertEquals(ErrorsEnum.INGREDIENTS_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldRemoveIngredientById(){
        var idMock = 1;
        Mockito.doNothing().when(recipeService).removeIngredient(anyInt(), any(Ingredient.class));
        var ingredientMock = mock(Ingredient.class);
        when(ingredientMock.getId()).thenReturn(idMock);
        when(ingredientRepository.findByRecipeIdAndId(anyInt(), anyInt())).thenReturn(Optional.of(ingredientMock));
        ingredientService.deleteById(idMock, idMock);
        Mockito.verify(recipeService, Mockito.times(1)).removeIngredient(anyInt(), any(Ingredient.class));
    }

    @Test
    void shouldThrowsNoIngredientsByRecipeAndIngredientIdOnRemove() {
        when(ingredientRepository.findByRecipeIdAndId(anyInt(), anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(IngredientException.class, () -> ingredientService.deleteById(anyInt(), anyInt()));
        Assertions.assertEquals(ErrorsEnum.INGREDIENTS_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldUpdateAnIngredient() {
        var idMock = 1;
        var unitMock = mock(Unit.class);
        when(unitMock.getId()).thenReturn(idMock);
        when(unitMock.getName()).thenReturn("Mock");
        when(unitMock.getInitials()).thenReturn("mck");
        var ingredientMock = mock(Ingredient.class);
        when(ingredientMock.withId(anyInt())).thenReturn(ingredientMock);
        when(ingredientMock.withAmount(any(BigDecimal.class))).thenReturn(ingredientMock);
        when(ingredientMock.withDescription(anyString())).thenReturn(ingredientMock);
        when(ingredientMock.withUnit(any(Unit.class))).thenReturn(ingredientMock);
        when(ingredientMock.withName(anyString())).thenReturn(ingredientMock);
        when(ingredientRepository.findByRecipeIdAndId(anyInt(), anyInt())).thenReturn(Optional.of(ingredientMock));
        var createIngredientMock = mock(CreateIngredientsDTO.class);
        when(createIngredientMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(createIngredientMock.getName()).thenReturn("Mock");
        when(createIngredientMock.getDescription()).thenReturn("Mock");
        when(createIngredientMock.getUnit()).thenReturn(mock(UnitDTO.class));
        ingredientService.update(idMock, idMock, createIngredientMock);
        Mockito.verify(ingredientRepository, Mockito.times(1)).save(any(Ingredient.class));
    }

    @Test
    void shouldThrowsNoIngredientsByRecipeAndIngredientIdOnUpdate() {
        var idMock = 1;
        when(ingredientRepository.findByRecipeIdAndId(anyInt(), anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(IngredientException.class, () -> ingredientService.update(idMock, idMock, mock(CreateIngredientsDTO.class)));
        Assertions.assertEquals(ErrorsEnum.INGREDIENTS_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}