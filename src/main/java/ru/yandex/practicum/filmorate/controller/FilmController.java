package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmValidationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmValidationService filmValidationService;

    @Autowired
    public FilmController(FilmValidationService filmValidationService) {
        this.filmValidationService = filmValidationService;

    }

    @PostMapping
    public Film saveNewFilm(@Valid @RequestBody Film film) {
        return filmValidationService.saveNewFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmValidationService.updateFilm(film);

    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmValidationService.getAllFilms();

    }


}


