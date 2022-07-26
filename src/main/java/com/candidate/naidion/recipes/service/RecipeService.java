package com.candidate.naidion.recipes.service;

import com.candidate.naidion.recipes.dto.CreateRecipeDTO;
import com.candidate.naidion.recipes.dto.RecipeDTO;
import com.candidate.naidion.recipes.dto.UpdateRecipeDTO;
import com.candidate.naidion.recipes.entity.Ingredient;
import com.candidate.naidion.recipes.entity.Recipe;
import com.candidate.naidion.recipes.enums.ErrorsEnum;
import com.candidate.naidion.recipes.exception.RecipeException;
import com.candidate.naidion.recipes.mapper.RecipeMapper;
import com.candidate.naidion.recipes.repository.RecipeRepository;
import com.candidate.naidion.recipes.specification.RecipeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public void create(CreateRecipeDTO createRecipeDTO) {
        var recipe = RecipeMapper.toRecipe(createRecipeDTO);
        recipeRepository.save(recipe);
    }

    public Page<RecipeDTO> findAll(Boolean vegetarian, Integer people, List<String> withIngredients, List<String> withoutIngredients, String instructions, Pageable pageable){
        var filter = RecipeSpecification.build(vegetarian, people, instructions, withIngredients, withoutIngredients);
        var recipes = recipeRepository.findAll(filter, pageable);
        return recipes.map(RecipeMapper::toRecipeDTO);
    }

    public RecipeDTO findById(Integer id) {
        var recipe = getRecipeById(id);
        return RecipeMapper.toRecipeDTO(recipe);
    }

    public void update(Integer id, UpdateRecipeDTO updateRecipeDTO) {
        var recipe = getRecipeById(id);
        recipe = recipe.withName(updateRecipeDTO.getName())
                .withInstructions(updateRecipeDTO.getInstructions())
                .withPeople(updateRecipeDTO.getPeople())
                .withVegetarian(updateRecipeDTO.isVegetarian());
        recipeRepository.save(recipe);
    }

    public void delete(Integer id) {
        var recipe = getRecipeById(id);
        recipeRepository.delete(recipe);
    }

    public void addIngredient(Integer id, Ingredient ingredient){
        var recipe = getRecipeById(id);
        recipe.getIngredients().add(ingredient);
        recipeRepository.save(recipe);
    }

    @Transactional
    public void removeIngredient(Integer id, Ingredient ingredient){
        var recipe = getRecipeById(id);
        recipe.getIngredients().removeIf(i -> i.getId().equals(ingredient.getId()));
        recipeRepository.save(recipe);
    }

    private Recipe getRecipeById(Integer id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeException(HttpStatus.NOT_FOUND, ErrorsEnum.RECIPE_NOT_FOUND_BY_ID, id));
    }
}
