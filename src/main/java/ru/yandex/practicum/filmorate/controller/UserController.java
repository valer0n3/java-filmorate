package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

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
    public User saveNewUser(@RequestBody User user) {
        int id = incrementId();
        user.setId(id);
        userMap.put(id, user);
        return user;
    }

    @PutMapping
    public User updateFilm(@RequestBody User user) {
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
}
