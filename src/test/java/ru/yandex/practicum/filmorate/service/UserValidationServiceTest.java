package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.userStorage;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidationServiceTest {
    private UserValidationService userValidationService;

    @BeforeEach
    void setUp() {
        userValidationService = new UserValidationService(new userStorage());
    }

    @Test
    public void ShouldThrowValidationExceptionWhenLoginHasSpaces() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidationService.checkLogin("tes t"));
        assertEquals("Login can't have \\s symbols", exception.getMessage(),
                "Exception ValidationException is not correctly thrown when login has spaces");
    }

    @Test
    public void ShouldReturnTrueWhenNameIsNullOrEmpty() {
        User user = new User();
        user.setName(null);
        assertTrue(userValidationService.checkIfNameIsEmpty(user),
                "Return incorrect False when user's name is null");
        user.setName(" ");
        assertTrue(userValidationService.checkIfNameIsEmpty(user),
                "Return incorrect False when user's name isBlanked");
    }
}