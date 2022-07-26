package com.candidate.naidion.recipes.resource;

import com.candidate.naidion.recipes.dto.CreateRecipeDTO;
import com.candidate.naidion.recipes.dto.RecipeDTO;
import com.candidate.naidion.recipes.dto.UpdateRecipeDTO;
import com.candidate.naidion.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Recipes", description = "Manage recipes")
@RestController
@RequestMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RecipeResource {

    private final RecipeService recipeService;

    @Operation(summary = "Show all recipes")
    @GetMapping
    public Page<RecipeDTO> findAll(@RequestParam(required = false) Boolean vegetarian,
                                   @RequestParam(required = false) Integer people,
                                   @RequestParam(required = false) List<String> withIngredients,
                                   @RequestParam(required = false) List<String> withoutIngredients,
                                   @RequestParam(required = false) String instructions,
                                   Pageable pageable) {
        return recipeService.findAll(vegetarian, people, withIngredients, withoutIngredients, instructions, pageable);
    }

    @Operation(summary = "Create a new recipe")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateRecipeDTO createRecipeDTO) {
        recipeService.create(createRecipeDTO);
    }

    @Operation(summary = "Get a recipe")
    @GetMapping("/{id}")
    public RecipeDTO findById(@PathVariable Integer id) {
        return recipeService.findById(id);
    }

    @Operation(summary = "Update a recipe")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Integer id, @Valid @RequestBody UpdateRecipeDTO updateRecipeDTO) {
        recipeService.update(id, updateRecipeDTO);
    }

    @Operation(summary = "Delete a recipe")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id) {
        recipeService.delete(id);
    }
}
