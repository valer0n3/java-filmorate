package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {
    private final MpaStorage mpaDbStorage;

    @Test
    public void getAllMpa() {
        List<Mpa> listOfMpa = mpaDbStorage.getAllMpa();
        assertEquals(5, listOfMpa.size(), "List size of Mpa's is not correct!");
        assertThat(listOfMpa.get(0)).hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "G");
        assertThat(listOfMpa.get(4)).hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("name", "NC-17");
    }

    @Test
    public void getMpaById() {
        Mpa mpa = mpaDbStorage.getMpaById(3);
        assertThat(mpa).hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("name", "PG-13");
    }

    @Test
    public void checkIfMpaExists() {
        assertTrue(mpaDbStorage.checkIfMpaExists(4), "Mpa is not existed, but should exist!");
        assertFalse(mpaDbStorage.checkIfMpaExists(-1), "Mpa is existed, but should not.");
    }
}