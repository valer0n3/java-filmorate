package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Integer, User> userMap = new HashMap();
    private int counter = 0;

    @PostMapping
    public User saveNewUser(@Valid @RequestBody User user) {
        checkLogin(user.getLogin());
        if (checkIfNameIsEmpty(user.getName())) {
            user.setName(user.getLogin());
        }
        checkBirthdayDate(user.getBirthday());
        int id = incrementId();
        user.setId(id);
        userMap.put(id, user);
        return user;
    }

    @PutMapping
    public User updateFilm(@Valid @RequestBody User user) {
        if (checkIfFilmExists(user.getId())) {
            userMap.put(user.getId(), user);
        }
        return user;
    }

    @GetMapping
    public List<User> getAllFilms() {
        return new ArrayList<>(userMap.values());
    }

    private int incrementId() {
        return ++counter;
    }

    private boolean checkIfFilmExists(int id) {
        for (int userMapKey : userMap.keySet()) {
            if (id == userMapKey) {
                return true;
            }
        }
        return false;
    }

    private void checkLogin(String login) {
        if (login.contains(" ")) {
            throw new ValidationException("Login can't have \" \" symbols");
        }
    }

    private boolean checkIfNameIsEmpty(String name) {
        return name.isBlank();

    }

    private void checkBirthdayDate(LocalDate birthday) {
        if (birthday.isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday date can't be in future");

        }

    }
}
