package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User saveNewUser(User user);

    User getUserById(long id);

    User updateUser(User user);

    List<User> getAllUsers();

    // void saveFriend(User user, User friendUser);
    void saveFriend(long id, long friendID);

    void deleteFriend(long id, long friendID);

    List<User> getFriendList(long id);

    List<User> getCommonFriendList(long id, long otherId);

    boolean checkIfUserExists(long userId);

    boolean checkIfFriendshipRecordExists(long id, long friendID);
}
