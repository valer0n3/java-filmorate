package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    User saveNewUser(User user);

    User getUserById(long id);

    User updateUser(User user);

    List<User> getAllUsers();

    void saveFriend(User user, User friendUser);

    void deleteFriend(User user, User friendUser);

    Set<Long> getFriendList(User user);
}
