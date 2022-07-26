package com.candidate.naidion.recipes.specification;

import com.candidate.naidion.recipes.entity.Ingredient;
import com.candidate.naidion.recipes.entity.Recipe;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@UtilityClass
public class RecipeSpecification {

    public static Specification<Recipe> build(Boolean vegetarian, Integer people, String instructions, List<String> withIngredients, List<String> withoutIngredients){
        return (root, query, cb) -> {
            var filter = emptyWhere();
            if(vegetarian != null) filter = filter.and(isVegetarian(vegetarian));
            if(people != null) filter = filter.and(equalsPeople(people));
            if(instructions != null) filter = filter.and(likeInstruction(instructions));
            if(!emptyIfNull(withIngredients).isEmpty()) filter = filter.and(withIngredients(withIngredients));
            if(!emptyIfNull(withoutIngredients).isEmpty()) filter = filter.and(withoutIngredients(withoutIngredients));
            return filter.toPredicate(root, query, cb);
        };
    }

    private static Specification<Recipe> emptyWhere(){
        return (root, query, cb) -> cb.equal(cb.literal(1), 1);
    }

    private static Specification<Recipe> isVegetarian(@NonNull Boolean vegetarian) {
        return (root, query, cb) -> cb.equal(root.get("vegetarian"), vegetarian);
    }

    private static Specification<Recipe> equalsPeople(@NonNull Integer people) {
        return (root, query, cb) -> cb.equal(root.get("people"), people);
    }

    private static Specification<Recipe> likeInstruction(@NonNull String instructions) {
        return (root, query, cb) -> cb.like(root.get("instructions"), "%" + instructions + "%");
    }

    private static Specification<Recipe> withIngredients(@NonNull List<String> ingredients) {
        return (root, query, cb) -> {
            var ingredientSubquery = query.subquery(Ingredient.class);
            var ingredientRoot = ingredientSubquery.from(Ingredient.class);
            ingredientSubquery.select(ingredientRoot).where(cb.equal(ingredientRoot.get("recipeId"), root.get("id")), ingredientRoot.get("name").in(ingredients));
            return cb.exists(ingredientSubquery);
        };
    }

    private static Specification<Recipe> withoutIngredients(@NonNull List<String> ingredients) {
        return (root, query, cb) -> {
            var ingredientSubquery = query.subquery(Ingredient.class);
            var ingredientRoot = ingredientSubquery.from(Ingredient.class);
            ingredientSubquery.select(ingredientRoot).where(cb.equal(ingredientRoot.get("recipeId"), root.get("id")), ingredientRoot.get("name").in(ingredients));
            return cb.not(cb.exists(ingredientSubquery));
        };
    }
}
