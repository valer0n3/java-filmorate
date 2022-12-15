package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserStorage userStorage;
    private final JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach() {
        String sqlRequest3 = "delete from FRINDSHIP";
        String sqlRequest4 = "alter table FRINDSHIP alter column FRINDSHIP_ID restart with 1";
        jdbcTemplate.update(sqlRequest3);
        jdbcTemplate.update(sqlRequest4);
        String sqlRequest = "delete from USERS";
        String sqlRequest1 = "alter table USERS alter column USERS_ID restart with 1";
        jdbcTemplate.update(sqlRequest);
        jdbcTemplate.update(sqlRequest1);
        User testUser = new User(1L, "test@email.ru", "testLogin",
                "testName", LocalDate.of(2010, 10, 24));
        userStorage.saveNewUser(testUser);
        String sqlInsert = "insert into USERS(EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlInsert, "test2@email.ru", "test2Login",
                "test2Name", LocalDate.of(2010, 10, 24));
    }

    @Test
    public void saveFriendShouldReturn2() {
        userStorage.saveFriend(1, 2);
        userStorage.saveFriend(2, 1);
        List<User> friendList = userStorage.getFriendList(1);
        assertEquals(2, friendList.get(0).getId(), "Friend Id is incorrect");
    }

    @Test
    public void saveNewUserAndGetUserByIdShouldIdEquals3() {
        String sqlInsert = "insert into USERS(EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlInsert, "test3@email.ru", "test3Login",
                "test3Name", LocalDate.of(2000, 5, 10));
        User savedUser = userStorage.getUserById(3);
        assertThat(savedUser).hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("login", "test3Login");
    }

    @Test
    public void updateUser() {
        User testUser = new User(1L, "updatedMail@email.ru", "updatedLogin",
                "updatedName", LocalDate.of(2010, 10, 24));
        userStorage.updateUser(testUser);
        User updatedUser = userStorage.getUserById(1);
        assertEquals("updatedMail@email.ru", updatedUser.getEmail(), "Email is incorrect");
        assertEquals("updatedLogin", updatedUser.getLogin(), "Login is incorrect");
        assertEquals("updatedName", updatedUser.getName(), "Name is incorrect");
    }
}