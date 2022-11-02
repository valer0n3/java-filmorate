package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> userMap = new HashMap<>();
    private int counter = 0;
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public User saveNewUser(@Valid @RequestBody User user) {
        checkLogin(user.getLogin());
        if (checkIfNameIsEmpty(user)) {
            user.setName(user.getLogin());
            log.warn("User name is empty. Login will be used as user name.");
        }
        int id = incrementId();
        user.setId(id);
        userMap.put(id, user);
        log.info("new User was successfully added!");
        return user;
    }

    @PutMapping
    public User updateFilm(@Valid @RequestBody User user) {
        if (checkIfUserIdExists(user.getId())) {
            userMap.put(user.getId(), user);
            log.info("new User was successfully updated!");
            return user;
        } else {
            log.info("new Film wasn't updated. Requested ID does not exists!");
            throw new ValidationException("new Film wasn't updated. Requested ID does not exists!");
        }
    }

    @GetMapping
    public List<User> getAllFilms() {
        return new ArrayList<>(userMap.values());
    }

    private int incrementId() {
        return ++counter;
    }

    private boolean checkIfUserIdExists(int id) {
        return userMap.containsKey(id);
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
