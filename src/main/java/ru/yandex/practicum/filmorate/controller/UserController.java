package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @PostMapping
    public User saveNewUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.saveNewUser(user);
    }

    @PutMapping
    public User updateFilm(@Valid @RequestBody User user) {
        return inMemoryUserStorage.updateFilm(user);
    }

    @GetMapping
    public List<User> getAllFilms() {
        return inMemoryUserStorage.getAllFilms();
    }


}
