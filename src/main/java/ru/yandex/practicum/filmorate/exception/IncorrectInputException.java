package ru.yandex.practicum.filmorate.exception;

public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String message) {
        super(message);
    }
}
