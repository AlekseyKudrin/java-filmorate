package ru.yandex.practicum.filmorate.exception;

public class IncorrectValueException extends RuntimeException{
    public IncorrectValueException(String message) {
        super(message);
    }
}
