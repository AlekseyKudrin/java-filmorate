package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerValidationException(final ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectValueException(final IncorrectValueException e) {
        return new ErrorResponse(e.getMessage());
    }
}