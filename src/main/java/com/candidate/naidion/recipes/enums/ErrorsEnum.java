package com.candidate.naidion.recipes.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorsEnum {

    NO_UNITS_FOUND("no.units.found"),
    UNIT_NOT_FOUND_BY_ID("unit.not.found.by.id"),
    INITIALS_ALREADY_EXISTS("initials.already.exists"),
    RECIPE_NOT_FOUND_BY_ID("recipe.not.found.by.id"),
    INGREDIENTS_NOT_FOUND_BY_RECIPE_ID("ingredients.not.found.by.recipes.id"),
    INGREDIENTS_NOT_FOUND_BY_ID("ingredients.not.found.by.id"),
    NO_INGREDIENT_FOUND_BY_ID_FOR_RECIPE_ID("no.ingredient.found.by.id.for.recipe.id"),
    ;

    private final String code;

}
