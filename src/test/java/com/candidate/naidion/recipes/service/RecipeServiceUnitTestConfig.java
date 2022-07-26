package com.candidate.naidion.recipes.service;

import com.candidate.naidion.recipes.config.UnitTestConfig;
import com.candidate.naidion.recipes.dto.CreateRecipeDTO;
import com.candidate.naidion.recipes.dto.UpdateRecipeDTO;
import com.candidate.naidion.recipes.entity.Ingredient;
import com.candidate.naidion.recipes.entity.Recipe;
import com.candidate.naidion.recipes.enums.ErrorsEnum;
import com.candidate.naidion.recipes.exception.RecipeException;
import com.candidate.naidion.recipes.repository.RecipeRepository;
import com.candidate.naidion.recipes.specification.RecipeSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("RecipeServiceTest")
class RecipeServiceUnitTestConfig extends UnitTestConfig {

    @MockBean
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeService recipeService;

    @Test
    void shouldCreateARecipe(){
        recipeService.create(mock(CreateRecipeDTO.class));
        Mockito.verify(recipeRepository, Mockito.times(1)).save(any(Recipe.class));
    }

    @Test
    void shouldListAllRecipes(){
        var specificationMockedStatic = Mockito.mockStatic(RecipeSpecification.class);
        specificationMockedStatic.when(() -> RecipeSpecification.build(null, null, null, null, null)).thenReturn(Specification.where(null));
        var recipes = new PageImpl<>(List.of(mock(Recipe.class), mock(Recipe.class)), mock(Pageable.class), 2);
        when(recipeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(recipes);
        var recipePage = recipeService.findAll(null, null, null, null, null, mock(Pageable.class));
        specificationMockedStatic.close();
        Assertions.assertEquals(recipes.getTotalElements(), recipePage.getTotalElements());
    }

    @Test
    void shouldFindById() {
        var idMock = 1;
        var recipeMock = mock(Recipe.class);
        when(recipeMock.getId()).thenReturn(idMock);
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(recipeMock));
        var recipeDTO = recipeService.findById(anyInt());
        Assertions.assertEquals(idMock, recipeDTO.getId());
    }

    @Test
    void shouldThrowsNoRecipeByIdExceptionOnFindById() {
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(RecipeException.class, () -> recipeService.findById(anyInt()));
        Assertions.assertEquals(ErrorsEnum.RECIPE_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldUpdateRecipe() {
        var idMock = 1;
        var updateMock = mock(UpdateRecipeDTO.class);
        when(updateMock.getPeople()).thenReturn(2);
        when(updateMock.getName()).thenReturn("mock");
        when(updateMock.getInstructions()).thenReturn("mock");
        when(updateMock.isVegetarian()).thenReturn(false);
        var recipeMock = mock(Recipe.class);
        when(recipeMock.withInstructions(anyString())).thenReturn(recipeMock);
        when(recipeMock.withName(anyString())).thenReturn(recipeMock);
        when(recipeMock.withPeople(anyInt())).thenReturn(recipeMock);
        when(recipeMock.withVegetarian(anyBoolean())).thenReturn(recipeMock);
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(recipeMock));
        recipeService.update(idMock, updateMock);
        Mockito.verify(recipeRepository, Mockito.times(1)).save(any(Recipe.class));
    }

    @Test
    void shouldThrowsNoRecipeByIdExceptionOnUpdate() {
        var idMock = 1;
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(RecipeException.class, () -> recipeService.update(idMock, mock(UpdateRecipeDTO.class)));
        Assertions.assertEquals(ErrorsEnum.RECIPE_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldDeleteRecipe() {
        var idMock = 1;
        var recipeMock = mock(Recipe.class);
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(recipeMock));
        recipeService.delete(idMock);
        Mockito.verify(recipeRepository, Mockito.times(1)).delete(any(Recipe.class));
    }

    @Test
    void shouldThrowsNoRecipeByIdExceptionOnDelete() {
        var idMock = 1;
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(RecipeException.class, () -> recipeService.delete(idMock));
        Assertions.assertEquals(ErrorsEnum.RECIPE_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }


    @Test
    void shouldAddAnIngredient(){
        var idMock = 1;
        var recipeMock = mock(Recipe.class);
        when(recipeMock.getIngredients()).thenReturn(new ArrayList<>());
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(recipeMock));
        recipeService.addIngredient(idMock, mock(Ingredient.class));
        Mockito.verify(recipeRepository, Mockito.times(1)).save(any(Recipe.class));
    }

    @Test
    void shouldThrowsNoRecipeByIdExceptionOnAddIngredient(){
        var idMock = 1;
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(RecipeException.class, () -> recipeService.addIngredient(idMock, mock(Ingredient.class)));
        Assertions.assertEquals(ErrorsEnum.RECIPE_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldThrowsNoRecipeByIdExceptionOnRemoveIngredient(){
        var idMock = 1;
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(RecipeException.class, () -> recipeService.removeIngredient(idMock, mock(Ingredient.class)));
        Assertions.assertEquals(ErrorsEnum.RECIPE_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldRemoveAnIngredient(){
        var idMock = 1;
        var recipeMock = mock(Recipe.class);
        var ingredientMock = mock(Ingredient.class);
        when(ingredientMock.getId()).thenReturn(idMock);
        when(recipeMock.getIngredients()).thenReturn(new ArrayList<>(Arrays.asList(ingredientMock)));
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(recipeMock));
        var ingredientMockParam = mock(Ingredient.class);
        when(ingredientMockParam.getId()).thenReturn(idMock);
        recipeService.removeIngredient(idMock, ingredientMockParam);
        Mockito.verify(recipeRepository, Mockito.times(1)).save(any(Recipe.class));
    }
}