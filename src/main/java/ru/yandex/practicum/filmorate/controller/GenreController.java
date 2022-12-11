package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    @GetMapping
    public List<Genre> getAllGenres() {
        return null;
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable("id") long id) {
        return null;
    }
}
