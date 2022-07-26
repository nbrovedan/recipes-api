package com.candidate.naidion.recipes.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@Value
@With
@JsonDeserialize(builder = IngredientDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class IngredientDTO {

    Integer id;
    String name;
    String description;
    BigDecimal amount;
    UnitDTO unit;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder{}
}
