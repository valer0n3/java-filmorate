package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> filmMap = new HashMap();
    private int counter = 0;
    private LocalDate earliestReleaseDate = LocalDate.parse("1985-12-28");

    @PostMapping
    public Film saveNewFilm(@Valid @RequestBody Film film) {
        checkMaxDescriptionLength(film.getDescription());
        checkReleaseDate(film.getReleaseDate());
        checkFilmDuration(film.getDuration());
        int id = incrementId();
        film.setId(id);
        filmMap.put(id, film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (checkIfFilmExists(film.getId())) {
            checkMaxDescriptionLength(film.getDescription());
            checkReleaseDate(film.getReleaseDate());
            checkFilmDuration(film.getDuration());
            filmMap.put(film.getId(), film);
        }
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(filmMap.values());
    }

    private int incrementId() {
        return ++counter;
    }

    private boolean checkIfFilmExists(int id) {
        for (int filmMapKey : filmMap.keySet()) {
            if (id == filmMapKey) {
                return true;
            }
        }
        return false;
    }

    private void checkMaxDescriptionLength(String description) {
        if (description.length() > 1) {
            throw new ValidationException("Description's length is more than 200 symbols!");
        }
    }

    private void checkReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(earliestReleaseDate)) {
            throw new ValidationException("Movie's release date can't be earlier than 1985-12-28");

        }
    }

    private void checkFilmDuration(Duration duration) {
        if (duration.toMillis() <= 0) {
            throw new ValidationException("Movie's duration can not be less than 0");

        }

    }
}

