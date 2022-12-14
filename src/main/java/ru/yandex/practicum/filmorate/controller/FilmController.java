package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FilmValidationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
    private final FilmValidationService filmValidationService;
    private final FilmService filmService;

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

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable("id") long filmId,
                         @PathVariable long userId) {
        filmService.likeFilm(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteFilmsLike(@PathVariable("id") long filmId,
                                @PathVariable long userId) {
        filmService.deleteFilmsLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopLikedFilms(@RequestParam(required = false) Integer count) {
        return filmService.getTopLikedFilms(count);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable long id) {
        return filmService.getFilmFromId(id);
    }
}


