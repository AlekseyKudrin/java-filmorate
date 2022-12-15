package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendsDao;
import ru.yandex.practicum.filmorate.storage.dao.UsersDao;

import java.util.*;

@Component
public class UserStorageInDb implements UserStorage {

    UsersDao usersDao;
    FriendsDao friendsDao;


    @Autowired
    public UserStorageInDb(UsersDao usersDao, FriendsDao friendsDao) {
        this.usersDao = usersDao;
        this.friendsDao = friendsDao;
    }

    @Override
    public Collection<User> getUserList() {
        return usersDao.getUserList();
    }

    @Override
    public void create(User user) {
        usersDao.create(user);
    }

    @Override
    public void change(User user) {
        validateId(user.getId());
        usersDao.change(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        validateId(id);
        return usersDao.getUserById(id);
    }

    @Override
    public Optional<User> getUser(User user) {
        return usersDao.getUserAllFields(user);
    }

    @Override
    public Collection<User> getCommonFriends(int id, int otherId) {
        Collection<Integer> generalFriendsList = friendsDao.getCommonFriends(id, otherId);
        return usersDao.getUserById(generalFriendsList);
    }

    @Override
    public void addInFriend(int id, int friendId) {
        validateId(id);
        validateId(friendId);
        friendsDao.addInFriend(id, friendId);
    }

    @Override
    public Collection<User> getAllFriends(int id) {
        Collection<Integer> friends = friendsDao.getAllFriends(id);
        return usersDao.getUserById(friends);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        friendsDao.deleteFriend(id, friendId);
    }

    private void validateId (int id) {
        if (usersDao.getUserById(id).isEmpty()) {
            throw new ValidationException("User not found");
        }
    }
}
