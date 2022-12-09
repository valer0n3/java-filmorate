package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component("daoForH2Database")
public class FilmDbStorage implements FilmStorage {
    @Override
    public Film saveNewFilm(Film film) {
        String sqlQuery = "INSERT INTO employees()" +
                "VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setLong(3, employee.getYearlyIncome());
            return stmt;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

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
