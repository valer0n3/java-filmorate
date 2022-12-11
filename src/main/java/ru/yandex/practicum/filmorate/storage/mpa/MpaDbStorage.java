package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sqlQueryAllGenreSelect = "select * from MPA";
        return jdbcTemplate.query(sqlQueryAllGenreSelect, (rs, rowNum) -> mapRowToListOfMpa(rs));
    }

    private Mpa mapRowToListOfMpa(ResultSet rs) throws SQLException {
        return new Mpa(rs.getLong("MPA_ID"),
                rs.getString("MPA_TYPE"));
    }

    @Override
    public Mpa getMpaById(long id) {
        String sqlQueryMpaSelect = "select * from MPA where MPA_ID = ?";
        return jdbcTemplate.queryForObject(sqlQueryMpaSelect, (rs, rowNum) -> mapRowToListOfMpa(rs), id);
    }

    @Override
    public boolean checkIfMpaExists(long id) {
        String sqlQuery = "select * from MPA where MPA_ID = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        return (sqlRowSet.next());
    }
}
