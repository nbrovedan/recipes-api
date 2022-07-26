package com.candidate.naidion.recipes.resource;

import com.candidate.naidion.recipes.dto.CreateIngredientsDTO;
import com.candidate.naidion.recipes.dto.IngredientDTO;
import com.candidate.naidion.recipes.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Ingredients", description = "Manage recipe's ingredients")
@RestController
@RequestMapping("/recipes/{recipeId}/ingredients")
@RequiredArgsConstructor
public class IngredientsResource {

    private final IngredientService ingredientService;

    @Operation(summary = "Add a new ingredient")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addIngredient(@PathVariable Integer recipeId,
                              @Valid @RequestBody CreateIngredientsDTO createIngredientsDTO){
        ingredientService.addIngredients(recipeId, createIngredientsDTO);
    }

    @Operation(summary = "Show all recipe ingredients")
    @GetMapping
    public List<IngredientDTO> listAll(@PathVariable Integer recipeId){
        return ingredientService.findAll(recipeId);
    }

    @Operation(summary = "Get a recipe ingredient")
    @GetMapping("/{ingredientId}")
    public IngredientDTO findById(@PathVariable Integer recipeId, @PathVariable Integer ingredientId){
        return ingredientService.findByRecipeIngredientId(recipeId, ingredientId);
    }

    @Operation(summary = "Remove a recipe ingredient")
    @DeleteMapping("/{ingredientId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer recipeId, @PathVariable Integer ingredientId){
        ingredientService.deleteById(recipeId, ingredientId);
    }

    @Operation(summary = "Update a recipe ingredient")
    @PutMapping("/{ingredientId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Integer recipeId,
                       @PathVariable Integer ingredientId,
                       @RequestBody CreateIngredientsDTO createIngredientsDTO){
        ingredientService.update(recipeId, ingredientId, createIngredientsDTO);
    }
}
