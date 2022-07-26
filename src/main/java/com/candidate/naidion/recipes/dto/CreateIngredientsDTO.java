package com.candidate.naidion.recipes.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Value
@With
@JsonDeserialize(builder = CreateIngredientsDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class CreateIngredientsDTO {

    @NotEmpty
    @Size(max = 150)
    String name;
    @Size(max = 255)
    String description;
    @NotNull
    BigDecimal amount;
    @NotNull
    UnitDTO unit;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder{}
}
