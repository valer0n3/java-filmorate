package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.IncorrectInputException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

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
        //  filmStorage.likeFilm(GetFilmFromId(filmId), GetUserFromId(userId));
        if (!filmStorage.checkIfFilmExists(filmId) || !userStorage.checkIfUserExists(userId)) { //TODO add checkIfUserNotExists
            log.warn("FilmID or UserID does not exists!");
            throw new ObjectNotFoundException("FilmID or UserID does not exists!");
        } else {
            filmStorage.likeFilm(filmId, userId);
            // checkIfFilmObjectIsNull(likedFilm);
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

    public Film GetFilmFromId(long id) {
        if (!filmStorage.checkIfFilmExists(id)) {
            log.warn("Film id does not exists!");
            throw new ObjectNotFoundException("Film object is not existed");
        } else {
            // checkIfFilmObjectIsNull(likedFilm);
            return filmStorage.getFilmByID(id);
        }
    }

/*    public User GetUserFromId(long id) {
        User user = userStorage.getUserById(id);
        checkIfUserObjectExists(user);
        return user;
    }*/

    public List<Film> getTopLikedMovies(Integer count) {
        count = checkIfCountIsAllowedValue(count);
        return filmStorage.getAllFilms().stream()
                .sorted((o1, o2) -> o2.setOfLikes.size() - o1.setOfLikes.size())
                .limit(count)
                .collect(Collectors.toList());
    }

/*    public Film getFilmById(long id) {
        Film likedFilm = filmStorage.getFilmByID(id);
        checkIfFilmObjectIsNull(likedFilm);
        return likedFilm;
    }*/

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
