package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserValidationService {
    private final UserStorage inMemoryUserStorage;
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserValidationService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User saveNewUser(User user) {
        checkLogin(user.getLogin());
        if (checkIfNameIsEmpty(user)) {
            user.setName(user.getLogin());
            log.warn("User name is empty. Login will be used as user name.");
        }
        inMemoryUserStorage.saveNewUser(user);
        log.info("new User was successfully added!");
        return user;
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
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
