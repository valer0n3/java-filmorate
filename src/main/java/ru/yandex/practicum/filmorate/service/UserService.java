package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.IncorrectInputException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(long id, long friendId) {
        checkIfIdsAreNotEqual(id, friendId);
        if (!userStorage.checkIfUserExists(id) || !userStorage.checkIfUserExists(friendId)) {
            log.warn("User or Friend object is not existed!");
            throw new ObjectNotFoundException("User or Friend object is not existed!");
        }
        if (userStorage.checkIfFriendshipRecordExists(id, friendId)) {
            log.warn("Friendship already exists");
            throw new IncorrectInputException("Friendship already exists");
        }
        userStorage.saveFriend(id, friendId);
    }

    public void deleteFriend(long id, long friendId) {
        checkIfIdsAreNotEqual(id, friendId);
        if (!userStorage.checkIfUserExists(id) || !userStorage.checkIfUserExists(friendId)) {
            log.warn("User or Friend object is not existed!");
            throw new ObjectNotFoundException("User or Friend object is not existed!");
        }
        if (!userStorage.checkIfFriendshipRecordExists(id, friendId)) {
            log.warn("Friendship already exists");
            throw new IncorrectInputException("Friendship already exists");
        }
        userStorage.deleteFriend(id, friendId);
    }

    public List<User> getFriendsList(long id) {
        if (!userStorage.checkIfUserExists(id)) {
            log.warn("User or Friend object is not existed!");
            throw new ObjectNotFoundException("User or Friend object is not existed!");
        }
        return userStorage.getFriendList(id);
    }

    public List<User> getCommonList(long id, long otherId) {
        if (!userStorage.checkIfUserExists(id) || !userStorage.checkIfUserExists(otherId)) {
            log.warn("User or Friend object is not existed!");
            throw new ObjectNotFoundException("User or Friend object is not existed!");
        }
        return userStorage.getCommonFriendList(id, otherId);
    }

    public User getUserById(long id) {
        if (!userStorage.checkIfUserExists(id)) {
            log.warn("User id does not exist!");
            throw new ObjectNotFoundException("User object does not exist!");
        } else {
            return userStorage.getUserById(id);
        }
    }

    private void checkIfIdsAreNotEqual(long id, long friendId) {
        if (id == friendId) {
            throw new IncorrectInputException("Id's can't be equals!");
        }
    }
}
