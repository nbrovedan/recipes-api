package com.candidate.naidion.recipes.exception;

import com.candidate.naidion.recipes.enums.ErrorsEnum;
import org.springframework.http.HttpStatus;

public class UnitException extends BusinessException {
    public UnitException(HttpStatus status, ErrorsEnum error, Object... args) {
        super(status, error, args);
    }

    public UnitException(HttpStatus status, ErrorsEnum error, Throwable throwable, Object... args) {
        super(status, error, throwable, args);
    }
}
