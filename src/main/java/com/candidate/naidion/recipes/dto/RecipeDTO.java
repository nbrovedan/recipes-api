package com.candidate.naidion.recipes.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;

@Value
@With
@JsonDeserialize(builder = RecipeDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class RecipeDTO {

    Integer id;
    String name;
    String instructions;
    Integer people;
    Boolean vegetarian;
    List<IngredientDTO> ingredients;


    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder{}
}
