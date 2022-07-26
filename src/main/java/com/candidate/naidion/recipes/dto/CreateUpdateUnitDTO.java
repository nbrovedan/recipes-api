package com.candidate.naidion.recipes.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@With
@JsonDeserialize(builder = CreateUpdateUnitDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class CreateUpdateUnitDTO {

    @NotEmpty
    @Size(max = 50)
    String name;
    @NotEmpty
    @Size(max = 10)
    String initials;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder{}
}
