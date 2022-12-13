package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendsDao {

    void addInFriend(int id, int friendId);
    Collection<Integer> getAllFriends(int id);

    Collection<Integer> getGeneralFriends(int id, int otherId);

    void deleteFriend(int id, int friendId);
}
