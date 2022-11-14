package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User saveNewUser(User user);

    User updateFilm(User user);

    List<User> getAllFilms();
}
