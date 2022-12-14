package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class MpaService {
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    private final MpaStorage mpaStorage;

    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    public Mpa getMpaById(long id) {
        if (!mpaStorage.checkIfMpaExists(id)) {
            log.warn("MpaId is not existed!");
            throw new ObjectNotFoundException("MpaId is not existed!");
        }
        return mpaStorage.getMpaById(id);
    }
}
