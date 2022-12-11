package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User saveNewUser(User user) {
        String sqlQueryInsertUser = "insert into USERS(EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryInsertUser, new String[]{"users_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        String sqlQueryUserUpdate = "update USERS set " +
                "EMAIL = ?," +
                "LOGIN = ?," +
                "NAME = ?," +
                "BIRTHDAY = ?" +
                "where USERS_ID = ?";
        jdbcTemplate.update(sqlQueryUserUpdate,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQueryUsersSelect = "select * from USERS";
        return jdbcTemplate.query(sqlQueryUsersSelect, (resultSet, rowNumber) -> mapRowToUser(resultSet));
    }

    private User mapRowToUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("USERS_ID"),
                resultSet.getString("EMAIL"),
                resultSet.getString("LOGIN"),
                resultSet.getString("NAME"),
                resultSet.getDate("BIRTHDAY").toLocalDate());
    }

    @Override
    public void saveFriend(User user, User friendUser) {
    }

    @Override
    public void deleteFriend(User user, User friendUser) {
    }

    @Override
    public Set<Long> getFriendList(User user) {
        return null;
    }

    @Override
    public boolean checkIfUserExists(long userId) {
        String sqlQueryUserSelect = "select count(*) from USERS where USERS_ID = ?";
        int result = jdbcTemplate.queryForObject(
                sqlQueryUserSelect, Integer.class, userId);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }
}
