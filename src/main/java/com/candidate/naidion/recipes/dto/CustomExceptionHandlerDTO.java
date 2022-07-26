package com.candidate.naidion.recipes.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.With;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Value
@With
@Getter
@JsonDeserialize(builder = CustomExceptionHandlerDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
@JsonPropertyOrder({"status","message","dateTime"})
public class CustomExceptionHandlerDTO {

    HttpStatus status;
    String message;
    LocalDateTime dateTime;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder{}
}
