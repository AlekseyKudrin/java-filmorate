package ru.yandex.practicum.filmorate.storage.dao;

import java.util.Collection;

public interface FriendsDao {

    void addInFriend(int id, int friendId);
    Collection<Integer> getAllFriends(int id);

    Collection<Integer> getCommonFriends(int id, int otherId);

    void deleteFriend(int id, int friendId);
}
