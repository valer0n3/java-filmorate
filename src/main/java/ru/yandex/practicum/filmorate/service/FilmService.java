package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    final UserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }



    public void likeFilm(long filmId, long userId) {
        Film likedFilm = filmStorage.getFilmByID(filmId);
        checkIfFilmObjectIsNull(likedFilm);
        User user = userStorage.getUserById(userId);
        checkIfUserObjectExists(user);
        filmStorage.likeFilm(likedFilm, userId);
    }

    public void deleteFilm(long filmId, long userId) {
        Film likedFilm = filmStorage.getFilmByID(filmId);
        checkIfFilmObjectIsNull(likedFilm);
        User user = userStorage.getUserById(userId);
        checkIfUserObjectExists(user);
        filmStorage.deleteFilmsLike(likedFilm, userId);
        System.out.println("******: " + likedFilm.setOfLikes);
    }

    private void checkIfUserObjectExists(User user) {
        if (user == null) {
            throw new ObjectNotFoundException("User object is not existed");
        }
    }

    private void checkIfFilmObjectIsNull(Film likedFilm) {
        if (likedFilm == null) {
            throw new ObjectNotFoundException("Film object is not existed");
        }
    }

}
