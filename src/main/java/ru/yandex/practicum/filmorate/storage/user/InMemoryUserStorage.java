package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> userMap = new HashMap<>();
    private int counter = 0;
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public User saveNewUser(User user) {
        long id = incrementId();
        user.setId(id);
        userMap.put(id, user);
        return user;
    }

    @Override
    public User getUserById(long id) {
        return userMap.get(id);
    }

    @Override
    public User updateUser(User user) {
        if (checkIfUserIdExists(user.getId())) {
            userMap.put(user.getId(), user);
            log.info("new User was successfully updated!");
            return user;
        } else {
            log.info("new Film wasn't updated. Requested ID does not exists!");
            throw new ValidationException("new Film wasn't updated. Requested ID does not exists!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void saveFriend(long id, long friendID) {
        //user.getSetOfFriends().add(friendUser.getId());
        //friendUser.getSetOfFriends().add(user.getId());
    }

    @Override
    public void deleteFriend(long id, long friendID) {
       // user.getSetOfFriends().remove(friendUser.getId());
       // friendUser.getSetOfFriends().remove(user.getId());
    }

    @Override
    public List<User> getFriendList(long id) {
        //return user.getSetOfFriends();
        return null;
    }

    @Override
    public boolean checkIfUserExists(long userId) {
        return false;
    }

    @Override
    public boolean checkIfFriendshipRecordExists(long id, long friendID) {
        return false;
    }

    private int incrementId() {
        return ++counter;
    }

    private boolean checkIfUserIdExists(long id) {
        return userMap.containsKey(id);
    }
}
