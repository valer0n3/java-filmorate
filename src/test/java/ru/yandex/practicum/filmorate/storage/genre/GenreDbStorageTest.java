package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {
    private final GenreStorage genreStorage;

    @Test
    void getAllGenres() {
        List<Genre> listOfGenre = genreStorage.getAllGenres();
        assertEquals(6, listOfGenre.size(), "List size of Genre's is not correct!");
        assertThat(listOfGenre.get(0)).hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Комедия");
        assertThat(listOfGenre.get(5)).hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("name", "Боевик");
    }

    @Test
    void getGenreById() {
        Genre genre = genreStorage.getGenreById(3);
        assertThat(genre).hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("name", "Мультфильм");
    }

    @Test
    void checkIfGenreExists() {
        assertTrue(genreStorage.checkIfGenreExists(4), "Genre is not existed, but should exist!");
        assertFalse(genreStorage.checkIfGenreExists(-1), "Genre is existed, but should not.");
    }
}