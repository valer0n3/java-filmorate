package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {
    private final FilmStorage filmStorage;
    private final JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach() {
        String sqlRequest3 = "delete from FILM_GENRE";
        String sqlRequest4 = "alter table FILM_GENRE alter column FILM_GENRE_ID restart with 1";
        jdbcTemplate.update(sqlRequest3);
        jdbcTemplate.update(sqlRequest4);
        String sqlRequest5 = "delete from LIKES";
        String sqlRequest6 = "alter table LIKES alter column LIKES_ID restart with 1";
        jdbcTemplate.update(sqlRequest5);
        jdbcTemplate.update(sqlRequest6);
        String sqlRequest = "delete from FILM";
        String sqlRequest1 = "alter table FILM alter column FILM_ID restart with 1";
        jdbcTemplate.update(sqlRequest);
        jdbcTemplate.update(sqlRequest1);
        Film testFilm = new Film(1L, "testFilm", "testDescription",
                LocalDate.of(2000, 5, 12), 200, new Mpa(2, "PG"),
                new ArrayList<Genre>());
        filmStorage.saveNewFilm(testFilm);
    }

    @Test
    public void saveNewFilmAndGetFilmByIdShouldIdEquals2() {
        Film testFilm = new Film(2L, "testFilm2", "testDescription2",
                LocalDate.of(2000, 5, 12), 200, new Mpa(2, "PG"),
                new ArrayList<Genre>());
        filmStorage.saveNewFilm(testFilm);
        Film savedFilm = filmStorage.getFilmByID(2);
        assertThat(savedFilm).hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("name", "testFilm2");
    }

    @Test
    public void updateFilm() {
        Film testFilm = new Film(1L, "testFilm3", "testDescription3",
                LocalDate.of(2000, 5, 12), 200, new Mpa(2, "PG"),
                new ArrayList<Genre>());
        filmStorage.updateFilm(testFilm);
        Film updatedFilm = filmStorage.getFilmByID(1);
        assertEquals("testFilm3", updatedFilm.getName(), "Name is incorrect");
        assertEquals("testDescription3", updatedFilm.getDescription(), "Description is incorrect");
    }
}