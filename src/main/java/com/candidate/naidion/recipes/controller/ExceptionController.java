package com.candidate.naidion.recipes.controller;

import com.candidate.naidion.recipes.dto.CustomExceptionHandlerDTO;
import com.candidate.naidion.recipes.exception.BusinessException;
import com.candidate.naidion.recipes.helper.MessageHelper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ExceptionController {
	
	@ExceptionHandler(BusinessException.class)
    public ResponseEntity<CustomExceptionHandlerDTO> handleAllBusinessExceptions(BusinessException ex) {
        return new ResponseEntity(CustomExceptionHandlerDTO.builder()
                .status(ex.getStatus())
                .dateTime(LocalDateTime.now())
                .message(MessageHelper.get(ex.getError(), ex.getArgs()))
                .build(), ex.getStatus());
    }
}
