package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> filmMap = new HashMap();

    @PostMapping
    public String saveNewFilm(@RequestBody Film film) {
        filmMap.put(film.getId(), film);
        return null;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(filmMap.values());
    }
}
