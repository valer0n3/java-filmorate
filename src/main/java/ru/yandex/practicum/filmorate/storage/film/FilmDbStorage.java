package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

@Repository("daoForH2Database")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film saveNewFilm(Film film) {
        String sqlQuery = "insert into FILM(MPA_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION) " +
                "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            if (film.getMpa() != null) {
                stmt.setLong(1, film.getMpa().getId());
            } else {
                stmt.setNull(1, Types.BIGINT);
            }
            stmt.setString(2, film.getName());
            stmt.setString(3, film.getDescription());
            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
            stmt.setString(5, String.valueOf(film.getDuration()));
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        if (film.getGenres() != null) {
            String sqlQueryGenres = "insert into FILM_GENRE(FILM_ID, GENRE_ID) values(?, ?)";
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(sqlQueryGenres, film.getId(), genre.getId());
            }
        }
        return film;
        //TODO check that genre ID or MPA id is within range 1-3
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
