package ru.yandex.practicum.filmorate.exception;

public class UncorrectedInputException extends RuntimeException {
    public UncorrectedInputException() {
    }

    public UncorrectedInputException(String message) {
        super(message);
    }
}
