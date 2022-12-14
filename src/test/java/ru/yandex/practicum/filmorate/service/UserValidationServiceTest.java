package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserValidationServiceTest {
    private final UserValidationService userValidationService;

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