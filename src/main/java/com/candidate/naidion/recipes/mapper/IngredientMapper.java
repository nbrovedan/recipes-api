package com.candidate.naidion.recipes.mapper;

import com.candidate.naidion.recipes.dto.CreateIngredientsDTO;
import com.candidate.naidion.recipes.dto.IngredientDTO;
import com.candidate.naidion.recipes.entity.Ingredient;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class IngredientMapper {

    public static IngredientDTO toIngredientDTO(Ingredient ingredient){
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .amount(ingredient.getAmount())
                .description(ingredient.getDescription())
                .name(ingredient.getName())
                .unit(UnitMapper.toUnitDTO(ingredient.getUnit()))
                .build();
    }

    public static List<IngredientDTO> toIngredientsDTO(List<Ingredient> ingredients){
        return CollectionUtils.emptyIfNull(ingredients).parallelStream()
                .map(IngredientMapper::toIngredientDTO)
                .collect(Collectors.toList());
    }

    public static Ingredient toIngredient(CreateIngredientsDTO createIngredientsDTO){
        return Ingredient.builder()
                .amount(createIngredientsDTO.getAmount())
                .description(createIngredientsDTO.getDescription())
                .name(createIngredientsDTO.getName())
                .unit(UnitMapper.toUnit(createIngredientsDTO.getUnit()))
                .build();
    }

    public static List<Ingredient> toIngredients(List<CreateIngredientsDTO> createIngredientsDTO) {
        return CollectionUtils.emptyIfNull(createIngredientsDTO).parallelStream()
                .map(IngredientMapper::toIngredient)
                .collect(Collectors.toList());
    }
}
