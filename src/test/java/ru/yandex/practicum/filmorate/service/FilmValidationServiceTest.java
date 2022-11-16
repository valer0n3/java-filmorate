package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmValidationServiceTest {
    private FilmValidationService filmValidationService;

    @BeforeEach
    void setUp() {
        filmValidationService = new FilmValidationService(new InMemoryFilmStorage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenReleaseDateIsEarlierThan1895_12_28() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmValidationService.checkReleaseDate(LocalDate.of(1895, 12, 27)));
        assertEquals("Movie's release date can't be earlier than 1985-12-28",
                exception.getMessage(),
                "Exception ValidationException is not correctly thrown when date is earlier than 1985-12-28");
    }

    @Test
    public void shouldThrowValidationExceptionWhenFilmDurationIsLessThan0() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmValidationService.checkFilmDuration(0));
        assertEquals("Movie's duration can not be less than 0", exception.getMessage(),
                "Exception ValidationException is not correctly thrown when movies duration = 0");
    }
}