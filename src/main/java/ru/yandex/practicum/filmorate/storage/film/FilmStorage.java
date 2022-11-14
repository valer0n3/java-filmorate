package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film saveNewFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

}
