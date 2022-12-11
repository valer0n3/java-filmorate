package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.IncorrectInputException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    final UserStorage userStorage;
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(long id, long friendId) {
        checkIfIdsAreNotEqual(id, friendId);
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(friendId);
        checkIfUserObjectIsNull(user);
        checkIfUserObjectIsNull(userFriend);
        userStorage.saveFriend(user, userFriend);
    }

    public void deleteFriend(long id, long friendId) {
        checkIfIdsAreNotEqual(id, friendId);
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(friendId);
        checkIfUserObjectIsNull(user);
        checkIfUserObjectIsNull(userFriend);
        userStorage.deleteFriend(user, userFriend);
    }

    public List<User> getFriendsList(long id) {
        User user = userStorage.getUserById(id);
        checkIfUserObjectIsNull(user);
        Set<Long> setOfFriends = userStorage.getFriendList(user);
        List<User> listOfFriends = new ArrayList<>();
        for (Long friendsId : setOfFriends) {
            User friend = userStorage.getUserById(friendsId);
            if (friend != null) {
                listOfFriends.add(friend);
            }
        }
        return listOfFriends;
    }

    public List<User> getCommonList(long id, long otherId) {
        checkIfIdsAreNotEqual(id, otherId);
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(otherId);
        checkIfUserObjectIsNull(user);
        checkIfUserObjectIsNull(userFriend);
        return user.getSetOfFriends().stream()
                .filter(commonId -> userFriend.getSetOfFriends().contains(commonId)).
                map(userStorage::getUserById).
                collect(Collectors.toList());
    }

    public User getUserById(long id) {
        if (!userStorage.checkIfUserExists(id)) {
            log.warn("User id does not exist!");
            throw new ObjectNotFoundException("User object does not exist!");
        } else {
            // checkIfFilmObjectIsNull(likedFilm);
            return userStorage.getUserById(id);
        }
    }

    private void checkIfIdsAreNotEqual(long id, long friendId) {
        if (id == friendId) {
            throw new IncorrectInputException("Id's can't be equals!");
        }
    }

    public void checkIfUserObjectIsNull(User user) {
        if (user == null) {
            throw new ObjectNotFoundException("User's id does not exist");
        }
    }
}
