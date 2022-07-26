package com.candidate.naidion.recipes.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Value
@With
@JsonDeserialize(builder = CreateRecipeDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class CreateRecipeDTO {

    @NotEmpty
    @Size(max = 150)
    String name;
    @NotNull
    String instructions;
    @NotNull
    @Min(1)
    Integer people;
    boolean vegetarian;
    @Valid
    List<CreateIngredientsDTO> ingredients;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder{}
}
