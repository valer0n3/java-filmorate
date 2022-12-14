package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    private final GenreStorage genreStorage;

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    public Genre getGenreById(long id) {
        if (!genreStorage.checkIfGenreExists(id)) {
            log.warn("GenreId is not existed!");
            throw new ObjectNotFoundException("GenreId is not existed!");
        }
        return genreStorage.getGenreById(id);
    }
}
