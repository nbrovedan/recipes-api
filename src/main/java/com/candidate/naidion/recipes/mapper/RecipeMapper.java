package com.candidate.naidion.recipes.mapper;

import com.candidate.naidion.recipes.dto.CreateRecipeDTO;
import com.candidate.naidion.recipes.dto.RecipeDTO;
import com.candidate.naidion.recipes.entity.Recipe;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RecipeMapper {

    public static RecipeDTO toRecipeDTO(Recipe recipe){
        return RecipeDTO.builder()
                .id(recipe.getId())
                .instructions(recipe.getInstructions())
                .name(recipe.getName())
                .people(recipe.getPeople())
                .vegetarian(recipe.isVegetarian())
                .ingredients(IngredientMapper.toIngredientsDTO(recipe.getIngredients()))
                .build();
    }

    public static Recipe toRecipe(CreateRecipeDTO createRecipeDTO) {
        return Recipe.builder()
                .name(createRecipeDTO.getName())
                .instructions(createRecipeDTO.getInstructions())
                .people(createRecipeDTO.getPeople())
                .vegetarian(createRecipeDTO.isVegetarian())
                .ingredients(IngredientMapper.toIngredients(createRecipeDTO.getIngredients()))
                .build();
    }
}
