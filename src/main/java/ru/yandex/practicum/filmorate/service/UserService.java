package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UncorrectedInputException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class UserService {

    final UserStorage userStorage;

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(long id, long friendId) {
        checkIfIdIsLessThan0(id, friendId);
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(friendId);
        checkIfUserObjectIsNull(user, userFriend);
        userStorage.saveFriend(user, userFriend);
    }

    public void deleteFriend(long id, long friendId) {
        checkIfIdIsLessThan0(id, friendId);
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(friendId);
        checkIfUserObjectIsNull(user, userFriend);
        userStorage.deleteFriend(user, userFriend);
    }

    public void checkIfIdIsLessThan0(long id, long friendId) {
        if (id < 0 || friendId < 0) {
            System.out.println("SomethingTEST");
            throw new UncorrectedInputException("Input variables (" + id + " and " + friendId + ") can't be less then 0");
        }
    }

    public void checkIfUserObjectIsNull(User user, User userFriend) {
        if (user == null || userFriend == null) {
            throw new ObjectNotFoundException("User's id does not exist");
        }
    }
}
