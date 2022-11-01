package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shouldThrowValidationExceptionWhenDescriptionLengthIsMoreThan200() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmController.checkMaxDescriptionLength("12345678910123456789101234567891012345678910" +
                        "1234567891012345678910123456789101234567891012345678910123456789101234567891012345678910" +
                        "12345678910123456789101234567891012345678910123456789101234567891012345678910"));
        assertEquals("Description's length is more than 200 symbols!", exception.getMessage(),
                "Exception ValidationException is not correctly " +
                        "thrown when description length is more than 200 symbols");
    }

    @Test
    public void shouldThrowValidationExceptionWhenReleaseDateIsEarlierThan1895_12_28() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmController.checkReleaseDate(LocalDate.of(1895, 12, 27)));
        assertEquals("Movie's release date can't be earlier than 1985-12-28",
                exception.getMessage(),
                "Exception ValidationException is not correctly thrown when date is earlier than 1985-12-28");
    }

    @Test
    public void shouldThrowValidationExceptionWhenFilmDurationIsLessThan0() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmController.checkFilmDuration(0));
        assertEquals("Movie's duration can not be less than 0", exception.getMessage(),
                "Exception ValidationException is not correctly thrown when movies duration = 0");
    }
}