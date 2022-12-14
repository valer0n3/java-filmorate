package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import java.util.List;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserValidationService {
    private final UserStorage userStorage;
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    public User saveNewUser(User user) {
        checkLogin(user.getLogin());
        if (checkIfNameIsEmpty(user)) {
            user.setName(user.getLogin());
            log.warn("User name is empty. Login will be used as user name.");
        }
        userStorage.saveNewUser(user);
        return user;
    }

    public User updateUser(User user) {
        if (!userStorage.checkIfUserExists(user.getId())) {
            log.warn("User id does not exist!");
            throw new ObjectNotFoundException("User object does not exist!");
        } else {
            return userStorage.updateUser(user);
        }
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    void checkLogin(String login) {
        if (Pattern.matches(".*\\s.*", login)) {
            log.warn("Login has \\s symbols.");
            throw new ValidationException("Login can't have \\s symbols");
        }
    }

    boolean checkIfNameIsEmpty(User user) {
        return user.getName() == null || user.getName().isBlank();
    }
}
