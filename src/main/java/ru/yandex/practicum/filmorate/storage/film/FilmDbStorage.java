package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public class FilmDbStorage implements FilmStorage{
    @Override
    public Film saveNewFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public List<Film> getAllFilms() {
        return null;
    }

    @Override
    public Film getFilmByID(long id) {
        return null;
    }

    @Override
    public void likeFilm(Film film, User user) {
    }

    @Override
    public void deleteFilmsLike(Film film, User user) {
    }
}
