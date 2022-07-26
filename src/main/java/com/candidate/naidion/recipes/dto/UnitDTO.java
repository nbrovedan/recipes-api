package com.candidate.naidion.recipes.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = UnitDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class UnitDTO {

    Integer id;
    String name;
    String initials;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder{}
}
