package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> filmMap = new HashMap();
    private int counter = 0;

    @PostMapping
    public Film saveNewFilm(@Valid @RequestBody Film film) {
        int id = incrementId();
        film.setId(id);
        filmMap.put(id, film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (checkIfFilmExists(film.getId())) {
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
}
