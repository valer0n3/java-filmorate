package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.*;

@Component
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
    public void saveFriend(User user, User friendUser) {
        user.getSetOfFriends().add(friendUser.getId());
        friendUser.getSetOfFriends().add(user.getId());

    }

    @Override
    public void deleteFriend(User user, User friendUser) {
        user.getSetOfFriends().remove(friendUser.getId());
        friendUser.getSetOfFriends().remove(user.getId());
    }

    @Override
    public Set<Long> getFriendList(User user) {
        return user.getSetOfFriends();
        //TODO change implementation
    }

    public Set<Long> getCommonFriendList(long id) {
        return null;
        //TODO change implementation
    }

    private int incrementId() {
        return ++counter;
    }

    private boolean checkIfUserIdExists(long id) {
        return userMap.containsKey(id);
    }


}
