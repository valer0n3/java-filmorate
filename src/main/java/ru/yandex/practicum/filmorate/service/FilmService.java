package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectInputException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

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

    public void deleteFilmsLike(long filmId, long userId) {
        Film likedFilm = filmStorage.getFilmByID(filmId);
        checkIfFilmObjectIsNull(likedFilm);
        User user = userStorage.getUserById(userId);
        checkIfUserObjectExists(user);
        filmStorage.deleteFilmsLike(likedFilm, userId);
    }

    public List<Film> getTopLikedMovies(Integer count) {
        count = checkIfCountIsAllowedValue(count);
        return filmStorage.getAllFilms().stream()
                .sorted((o1, o2) -> o2.setOfLikes.size() - o1.setOfLikes.size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film getFilmById(long id) {
        Film likedFilm = filmStorage.getFilmByID(id);
        checkIfFilmObjectIsNull(likedFilm);
        return likedFilm;
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
