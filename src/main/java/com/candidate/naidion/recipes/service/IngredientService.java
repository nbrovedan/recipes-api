package com.candidate.naidion.recipes.service;

import com.candidate.naidion.recipes.dto.CreateIngredientsDTO;
import com.candidate.naidion.recipes.dto.IngredientDTO;
import com.candidate.naidion.recipes.enums.ErrorsEnum;
import com.candidate.naidion.recipes.exception.IngredientException;
import com.candidate.naidion.recipes.mapper.IngredientMapper;
import com.candidate.naidion.recipes.mapper.UnitMapper;
import com.candidate.naidion.recipes.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeService recipeService;

    public void addIngredients(Integer recipeId, CreateIngredientsDTO createIngredientsDTO) {
        var ingredient = IngredientMapper.toIngredient(createIngredientsDTO);
        recipeService.addIngredient(recipeId, ingredient);
    }

    public List<IngredientDTO> findAll(Integer recipeId) {
        var ingredients = ingredientRepository.findAllByRecipeId(recipeId);
        if(ingredients.isEmpty()){
            throw new IngredientException(HttpStatus.NOT_FOUND, ErrorsEnum.INGREDIENTS_NOT_FOUND_BY_RECIPE_ID, recipeId);
        }
        return ingredients.parallelStream()
                .map(IngredientMapper::toIngredientDTO)
                .collect(Collectors.toList());
    }

    public IngredientDTO findByRecipeIngredientId(Integer recipeId, Integer ingredientId) {
        var ingredient = ingredientRepository.findByRecipeIdAndId(recipeId, ingredientId)
                .orElseThrow(() -> new IngredientException(HttpStatus.NOT_FOUND, ErrorsEnum.INGREDIENTS_NOT_FOUND_BY_ID, ingredientId));
        return IngredientMapper.toIngredientDTO(ingredient);
    }

    public void deleteById(Integer recipeId, Integer ingredientId) {
        var ingredient = ingredientRepository.findByRecipeIdAndId(recipeId, ingredientId)
                .orElseThrow(() -> new IngredientException(HttpStatus.NOT_FOUND, ErrorsEnum.INGREDIENTS_NOT_FOUND_BY_ID, ingredientId));
        recipeService.removeIngredient(recipeId, ingredient);
    }

    public void update(Integer recipeId, Integer ingredientId, CreateIngredientsDTO createIngredientsDTO) {
        var ingredient = ingredientRepository.findByRecipeIdAndId(recipeId, ingredientId)
                .orElseThrow(() -> new IngredientException(HttpStatus.NOT_FOUND, ErrorsEnum.INGREDIENTS_NOT_FOUND_BY_ID, ingredientId));
        ingredient = ingredient.withAmount(createIngredientsDTO.getAmount())
                .withDescription(createIngredientsDTO.getDescription())
                .withName(createIngredientsDTO.getName())
                .withUnit(UnitMapper.toUnit(createIngredientsDTO.getUnit()));
        ingredientRepository.save(ingredient);
    }
}
