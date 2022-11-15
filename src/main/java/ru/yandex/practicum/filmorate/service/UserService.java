package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UncorrectedInputException;

@Service
public class UserService {

    public void addFriend(long id, long friendId) {
        if (id < 0 || friendId < 0) {
            System.out.println("SomethingTEST");
            throw new UncorrectedInputException("Input variables (" + id + " and " + friendId + ") can't be less then 0");
        }
    }
}
