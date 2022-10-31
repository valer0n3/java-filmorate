package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> filmMap = new HashMap<>();
    private int counter = 0;
    private final static LocalDate EARLIEST_RELEASE_DATE = LocalDate.parse("1895-12-28");
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    @PostMapping
    public Film saveNewFilm(@Valid @RequestBody Film film) {
        checkMaxDescriptionLength(film.getDescription());
        checkReleaseDate(film.getReleaseDate());
        checkFilmDuration(film.getDuration());
        int id = incrementId();
        film.setId(id);
        filmMap.put(id, film);
        log.info("new Film was successfully added!");
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (checkIfFilmIdExists(film.getId())) {
            checkMaxDescriptionLength(film.getDescription());
            checkReleaseDate(film.getReleaseDate());
            checkFilmDuration(film.getDuration());
            filmMap.put(film.getId(), film);
            log.info("new Film was successfully updated!");
            return film;
        } else {
            log.info("new Film wasn't updated. Requested ID does not exists!");
            throw new ValidationException("new Film wasn't updated. Requested ID does not exists!");
        }
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(filmMap.values());
    }

    private int incrementId() {
        return ++counter;
    }

    public boolean checkIfFilmIdExists(int id) {
        for (int filmMapKey : filmMap.keySet()) {
            if (id == filmMapKey) {
                return true;
            }
        }
        return false;
    }

    public void checkMaxDescriptionLength(String description) {
        if (description.length() > 200) {
            log.warn("Description's length is more than 200 symbols!");
            throw new ValidationException("Description's length is more than 200 symbols!");
        }
    }

    public void checkReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(EARLIEST_RELEASE_DATE)) {
            log.warn("Movie's release date can't be earlier than 1895-12-28");
            throw new ValidationException("Movie's release date can't be earlier than 1985-12-28");
        }
    }

    public void checkFilmDuration(int duration) {
        if (duration <= 0) {
            log.warn("Movie's duration can not be less than 0");
            throw new ValidationException("Movie's duration can not be less than 0");
        }
    }
}


