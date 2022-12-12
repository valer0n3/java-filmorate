package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film saveNewFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    Film getFilmByID(long id);

    void likeFilm(long filmID, long userID);

    void deleteFilmsLike(long filmID, long userID);

    boolean checkIfFilmExists(long filmId);

    List<Film> getTopLikedFilms(int count);
}
