package com.candidate.naidion.recipes.exception;

import com.candidate.naidion.recipes.enums.ErrorsEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final ErrorsEnum error;
    private final Object[] args;
    private final HttpStatus status;

    protected BusinessException(HttpStatus status, ErrorsEnum error, Object... args) {
        super();
        this.error = error;
        this.args = args;
        this.status = status;
    }

    protected BusinessException(HttpStatus status, ErrorsEnum error, Throwable throwable, Object... args) {
        super(throwable);
        this.error = error;
        this.args = args;
        this.status = status;
    }
}
