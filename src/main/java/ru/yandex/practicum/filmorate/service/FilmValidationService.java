package ru.yandex.practicum.filmorate.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

@Service
public class FilmValidationService {
    private final static LocalDate EARLIEST_RELEASE_DATE = LocalDate.parse("1895-12-28");
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    private final FilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmValidationService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Film saveNewFilm(Film film) {
        checkReleaseDate(film.getReleaseDate());
        checkFilmDuration(film.getDuration());
        inMemoryFilmStorage.saveNewFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        checkReleaseDate(film.getReleaseDate());
        checkFilmDuration(film.getDuration());
        return inMemoryFilmStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    void checkReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(EARLIEST_RELEASE_DATE)) {
            log.warn("Movie's release date can't be earlier than 1895-12-28");
            throw new ValidationException("Movie's release date can't be earlier than 1985-12-28");
        }
    }

    void checkFilmDuration(int duration) {
        if (duration <= 0) {
            log.warn("Movie's duration can not be less than 0");
            throw new ValidationException("Movie's duration can not be less than 0");
        }
    }
}
