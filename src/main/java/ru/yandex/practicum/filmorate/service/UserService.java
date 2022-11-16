package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectInputException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    final UserStorage userStorage;

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(long id, long friendId) {
        checkifIdsAreNotEqual(id, friendId);
        checkIfIdIsLessThan0(id, friendId);
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(friendId);
        checkIfUserObjectIsNull(user);
        checkIfUserObjectIsNull(userFriend);
        userStorage.saveFriend(user, userFriend);
        System.out.println(user.getSetOfFriends());
        System.out.println(userFriend.getSetOfFriends());
    }


    public void deleteFriend(long id, long friendId) {
        checkifIdsAreNotEqual(id, friendId);
        checkIfIdIsLessThan0(id, friendId);
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(friendId);
        checkIfUserObjectIsNull(user);
        checkIfUserObjectIsNull(userFriend);
        userStorage.deleteFriend(user, userFriend);
        System.out.println(user.getSetOfFriends());
        System.out.println(userFriend.getSetOfFriends());
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
        checkifIdsAreNotEqual(id, otherId);
        checkIfIdIsLessThan0(id, otherId);
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(otherId);
        checkIfUserObjectIsNull(user);
        checkIfUserObjectIsNull(userFriend);
        checkIfSetOfFriendsIsEmpty(user, userFriend);
        return user.getSetOfFriends().stream()
                .filter(commonId -> userFriend.getSetOfFriends().contains(commonId)).
                map(userStorage::getUserById).
                collect(Collectors.toList());

    }

    private void checkIfSetOfFriendsIsEmpty(User user, User userFriend) {
        if (user.getSetOfFriends().isEmpty() || userFriend.getSetOfFriends().isEmpty()) {
            throw new ObjectNotFoundException("Общих друзей нет");
        }
    }


    public void checkIfIdIsLessThan0(long id, long friendId) {
        if (id < 0 || friendId < 0) {
            System.out.println("SomethingTEST");
            throw new IncorrectInputException("Input variables (" + id + " and " + friendId + ") can't be less then 0");
        }
    }

    private void checkifIdsAreNotEqual(long id, long friendId) {
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
