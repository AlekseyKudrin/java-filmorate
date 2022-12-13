package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Optional<User> create(User user) {
        validateOfUser(user);
        userStorage.create(user);
        log.info("User successfully created");
        return userStorage.getUser(user);
    }

    public Optional<User> change(User user) {
        validateOfUser(user);
        userStorage.change(user);
        log.info("User successfully change");
        return userStorage.getUser(user);
    }

    public Collection<User> getUsersList() {
        Collection<User> collectionUser = userStorage.getUserList();
        log.info("Return user list successfully");
        return collectionUser;
    }

    public Optional<User> getUser(int id) {
        Optional<User> user= userStorage.getUserById(id);
        log.info("Return user by Id successfully");
        return user;
    }

    public void addInFriend(int id, int friendId) {
        userStorage.addInFriend(id, friendId);
    }

    public void deleteFriend(int id, int friendId) {
        userStorage.deleteFriend(id, friendId);
        log.info("Friend delete successfully");
    }

    public Collection<User> getFriends(int id) {
        Collection<User> friendsList = userStorage.getAllFriends(id);
        log.info("Return list friends successfully");
        return friendsList;
    }

    public Collection<User> getFriends(int id, int otherId) {
        Collection<User> userList = userStorage.getGeneralFriends(id, otherId);
        log.info("Return general list friends successfully");
        return userList;
    }

    private void validateOfUser(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.warn("Error in email: email cannot be empty and must contains character @");
            throw new IncorrectValueException("Email empty or not contains character @");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.warn("Error in login: login cannot be empty and not must contains character space");
            throw new IncorrectValueException("login cannot be empty or not must contains character space");
        }
        if (user.getName() == null || user.getName().length() == 0) {
            user.setName(user.getLogin());
            log.info("The user specified an empty name. Default name = " + user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Error in date birthday: date birthday can't be after time now");
            throw new IncorrectValueException("Date birthday can't be after time now");
        }
    }
}
