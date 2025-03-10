package com.movieapp.exception.handling.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessages {
    MOVIE_NOT_FOUND("Movie not found", HttpStatus.NOT_FOUND),
    MOVIE_ALREADY_EXISTS("Movie already exists", HttpStatus.CONFLICT),
    EMAIL_ALREADY_IN_USE("Error: Email is already in use!", HttpStatus.CONFLICT);
    private final String message;
    private final HttpStatus status;

    ErrorMessages(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}