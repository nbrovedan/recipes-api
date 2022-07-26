package com.candidate.naidion.recipes.repository;


import com.candidate.naidion.recipes.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    List<Ingredient> findAllByRecipeId(Integer recipeId);
    Optional<Ingredient> findByRecipeIdAndId(Integer recipeId, Integer id);
}
