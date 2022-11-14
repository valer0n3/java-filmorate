package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserValidationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserValidationService userValidationService;

    @Autowired
    public UserController(UserValidationService userValidationService ) {
        this.userValidationService = userValidationService;
    }

    @PostMapping
    public User saveNewUser(@Valid @RequestBody User user) {
        return userValidationService.saveNewUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userValidationService.updateUser(user);
    }

    @GetMapping
    public List<User> getAllFilms() {
        return userValidationService.getAllUsers();
    }


}
