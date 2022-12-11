package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> filmMap = new HashMap<>();
    private int counter = 0;
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    @Override
    public Film saveNewFilm(Film film) {
        long id = incrementId();
        film.setId(id);
        filmMap.put(id, film);
        log.info("new Film was successfully added!");
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (checkIfFilmIdExists(film.getId())) {
            filmMap.put(film.getId(), film);
            log.info("new Film was successfully updated!");
            return film;
        } else {
            log.info("new Film wasn't updated. Requested ID does not exists!");
            throw new ValidationException("new Film wasn't updated. Requested ID does not exists!");
        }
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public Film getFilmByID(long id) {
        return filmMap.get(id);
    }

    @Override
    public void likeFilm(long filmID, long userID) {
       // film.setOfLikes.add(user.getId());
    }

    @Override
    public void deleteFilmsLike(long filmID, long userID) {
       // film.setOfLikes.remove(user.getId());
    }

    @Override
    public boolean checkIfFilmExists(long filmID) {
        return false;
    }

    private boolean checkIfFilmIdExists(long id) {
        return filmMap.containsKey(id);
    }

    private int incrementId() {
        return ++counter;
    }
}
