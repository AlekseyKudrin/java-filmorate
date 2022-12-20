package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    void create(User user);

    void change(User user);

    Collection<User> getUserList();

    Optional<User> getUserById(int id);

    Optional<User> getUser(User user);

    void addInFriend(int id, int friendId);

    Collection<User> getAllFriends(int id);

    Collection<User> getCommonFriends(int id, int otherId);

    void deleteFriend(int id, int friendId);
}
