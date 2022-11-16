package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }



    public void likeFilm(long filmId, long userId) {
        Film likedFilm = filmStorage.getFilmByID(filmId);
        checkIfFilmObjectIsNull(likedFilm);
        filmStorage.likeFilm(likedFilm, userId);
    }

    private void checkIfFilmObjectIsNull(Film likedFilm) {
        if (likedFilm == null) {
            throw new ObjectNotFoundException("Film object is not existed");
        }
    }

}
