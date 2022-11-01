package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest {
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void ShouldThrowValidationExceptionWhenLoginHasSpaces() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userController.checkLogin("tes t"));
        assertEquals("Login can't have \" \" symbols", exception.getMessage(),
                "Exception ValidationException is not correctly thrown when login has spaces");
    }

    @Test
    public void ShouldReturnTrueWhenNameIsNullOrEmpty() {
        User user = new User();
        user.setName(null);
        assertTrue(userController.checkIfNameIsEmpty(user)
                , "Return incorrect False when user's name is null");
        user.setName(" ");
        assertTrue(userController.checkIfNameIsEmpty(user)
                , "Return incorrect False when user's name isBlanked");
    }

    @Test
    public void ShouldThrowValidationExceptionWhenDateOfBirthIsAfterNow() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userController.checkDateOfBirth(LocalDate.now().plusDays(1)));
        assertEquals("Date of birth can't be in future", exception.getMessage(),
                "Exception ValidationException is not correctly " +
                        "thrown when date of birth is after now");
    }
}