package com.candidate.naidion.recipes.dto;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@With
@Builder(builderClassName = "JacksonBuilder")
public class UpdateRecipeDTO {

    @NotEmpty
    @Size(max = 150)
    String name;
    @NotNull
    String instructions;
    @NotNull
    @Min(1)
    Integer people;
    boolean vegetarian;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder{}
}
