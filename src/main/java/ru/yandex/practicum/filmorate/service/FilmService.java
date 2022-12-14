package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.IncorrectInputException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    final UserStorage userStorage;
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void likeFilm(long filmId, long userId) {
        if (!filmStorage.checkIfFilmExists(filmId) || !userStorage.checkIfUserExists(userId)) {
            log.warn("FilmID or UserID does not exists!");
            throw new ObjectNotFoundException("FilmID or UserID does not exists!");
        } else {
            filmStorage.likeFilm(filmId, userId);
        }
    }

    public void deleteFilmsLike(long filmId, long userId) {
        if (!filmStorage.checkIfFilmExists(filmId) || !userStorage.checkIfUserExists(userId)) {
            log.warn("FilmID or UserID does not exists!");
            throw new ObjectNotFoundException("FilmID or UserID does not exists!");
        } else {
            filmStorage.deleteFilmsLike(filmId, userId);
        }
    }

    public Film getFilmFromId(long id) {
        if (!filmStorage.checkIfFilmExists(id)) {
            log.warn("Film id does not exists!");
            throw new ObjectNotFoundException("Film object is not existed");
        } else {
            return filmStorage.getFilmByID(id);
        }
    }

    public List<Film> getTopLikedFilms(Integer count) {
        count = checkIfCountIsAllowedValue(count);
        return filmStorage.getTopLikedFilms(count);
    }

    private int checkIfCountIsAllowedValue(Integer count) {
        if (count == null) {
            return 10;
        }
        if (count < 0) {
            throw new IncorrectInputException("Parameter (count) can't be less than 0");
        }
        return count;
    }
}
