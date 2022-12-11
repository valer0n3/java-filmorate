package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlQueryAllGenreSelect = "select * from GENRE";
        return jdbcTemplate.query(sqlQueryAllGenreSelect, (rs, rowNum) -> mapRowToListOfGenres(rs));
    }

    private Genre mapRowToListOfGenres(ResultSet rs) throws SQLException {
        return new Genre(rs.getLong("GENRE_ID"),
                rs.getString("NAME"));
    }

    @Override
    public Genre getGenreById(long id) {
        String sqlQueryGenreSelect = "select * from GENRE where GENRE_ID = ?";
        return jdbcTemplate.queryForObject(sqlQueryGenreSelect, (rs, rowNum) -> mapRowToListOfGenres(rs), id);
    }

    @Override
    public boolean checkIfGenreExists(long id) {
        String sqlQuery = "select * from GENRE where GENRE_ID = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (sqlRowSet.getRow() == 0) {
            return false;
        } else return true;
    }
}
