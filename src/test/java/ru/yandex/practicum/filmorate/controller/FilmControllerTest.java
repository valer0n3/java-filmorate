package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    @BeforeEach
    void setUp() {
        FilmController filmController = new FilmController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shouldSetEpicStatusToNewAfterSubtasksListIsEmpty() {
    }
}