package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage {
    Film saveNewFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    Film getFilmByID(long id);

    void likeFilm(Film film, User user);

    void deleteFilmsLike(Film film, User user);

     boolean checkIfFilmExists(long filmId);
}
