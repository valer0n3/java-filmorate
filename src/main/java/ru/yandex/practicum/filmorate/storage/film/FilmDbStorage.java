package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository("daoForH2Database")
@AllArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

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
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQueryFilmUpdate = "update FILM set " +
                "MPA_ID = ?," +
                "NAME = ?," +
                "DESCRIPTION = ?," +
                "RELEASE_DATE = ?," +
                "DURATION = ?" +
                "where FILM_ID = ?";
        jdbcTemplate.update(sqlQueryFilmUpdate,
                checkIfMPANotNull(film),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());
        if (film.getGenres() != null) {
            String sqlQueryGenresDelete = "delete from FILM_GENRE where FILM_ID = ?";
            jdbcTemplate.update(sqlQueryGenresDelete, film.getId());
            String sqlQueryGenresInsert = "insert into FILM_GENRE(FILM_ID, GENRE_ID) values(?, ?)";
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(sqlQueryGenresInsert, film.getId(), genre.getId());
            }
        }
        return film;
    }

    private Long checkIfMPANotNull(Film film) {
        if (film.getMpa() != null) {
            return film.getMpa().getId();
        } else return null;
    }

    @Override
    public boolean checkIfFilmExists(long filmId) {
        String sqlQueryFilmSelect = "select count(*) from FILM where FILM_ID = ?";
        int result = jdbcTemplate.queryForObject(
                sqlQueryFilmSelect, Integer.class, filmId);
        return result == 1;
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQueryFilmsSelect = "select * from FILM as film" +
                " inner join MPA as mpa" +
                " ON film.MPA_ID = mpa.MPA_ID";
        List<Film> listOfFilms = jdbcTemplate.query(sqlQueryFilmsSelect, (resultSet, rowNumber) -> mapRowToFilm(resultSet));
        return listOfFilms;
    }

    private Film mapRowToFilm(ResultSet resultSet) throws SQLException {
        return new Film(resultSet.getLong("FILM_ID"),
                resultSet.getString("NAME"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getDate("RELEASE_DATE").toLocalDate(),
                resultSet.getInt("DURATION"),
                new Mpa(resultSet.getLong("MPA_ID"), resultSet.getString("MPA_TYPE")),
                mapRowToListOfGenres(resultSet.getLong("FILM_ID"))
        );
    }

    private List<Genre> mapRowToListOfGenres(long film_id) {
        String sqlQuerySelectFilmGenre = "select * from FILM_GENRE as filmgenre " +
                "join GENRE as genre " +
                "on filmgenre.GENRE_ID = genre.GENRE_ID " +
                "where filmgenre.FILM_ID = ?";
        List<Genre> genreList = jdbcTemplate.query(sqlQuerySelectFilmGenre,
                (resultSet, rowNumber) ->
                        new Genre(resultSet.getLong("GENRE.GENRE_ID"),
                                resultSet.getString(("GENRE.NAME"))), film_id);
        return genreList;
    }

    @Override
    public Film getFilmByID(long id) {
        String sqlQuerySelectFilmByID = "select * from FILM as film" +
                " inner join MPA as mpa" +
                " ON film.MPA_ID = mpa.MPA_ID where film.FILM_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuerySelectFilmByID, (resultSet, rowNumber) -> mapRowToFilm(resultSet),
                id);
    }

    @Override
    public void likeFilm(long filmID, long userID) {
        String sqlQueryLikesDelete = "delete from LIKES where FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(sqlQueryLikesDelete, filmID, userID);
        String sqlQueryLikesInsert = "insert into LIKES(FILM_ID, USER_ID) values(?, ?)";
        jdbcTemplate.update(sqlQueryLikesInsert, filmID, userID);
    }

    @Override
    public void deleteFilmsLike(long filmID, long userID) {
        String sqlQueryLikeDelete = "delete from LIKES where FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(sqlQueryLikeDelete, filmID, userID);
    }

    @Override
    public List<Film> getTopLikedFilms(int count) {
        @SuppressWarnings("SqlAggregates") String sqlQuerySelectFilmByID = "select  count(film.FILM_ID) as countfilms," +
                " film.*, M.*, likes.FILM_ID" +
                " from FILM as film" +
                " left join LIKES likes on film.FILM_ID = likes.FILM_ID" +
                " inner join MPA M on film.MPA_ID = M.MPA_ID" +
                " GROUP BY Film.FILM_ID ORDER BY countfilms, likes.FILM_ID" +
                " DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuerySelectFilmByID,
                (resultSet, rowNumber) -> mapRowToFilm(resultSet), count);
    }
}
