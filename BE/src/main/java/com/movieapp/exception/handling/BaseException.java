package com.movieapp.exception.handling;

import com.movieapp.exception.handling.enums.ErrorMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorMessages errorMessage;

    public BaseException(ErrorMessages errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return errorMessage.getStatus();
    }
}