package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FilmValidationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmValidationService filmValidationService;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmValidationService filmValidationService, FilmService filmService) {
        this.filmValidationService = filmValidationService;
        this.filmService = filmService;

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


    @PutMapping("/fipms/{id}/like/{userId}")
    public void likeFilm(@PathVariable long id,
                         @PathVariable long userId) {

        //TODO write implementation.
    }


}


