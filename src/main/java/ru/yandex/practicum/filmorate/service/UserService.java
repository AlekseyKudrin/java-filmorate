package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class UserService {
    public UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void create(User user) {
        validateOfUser(user);
        userStorage.add(user);
    }

    public void change(User user) {
        validateOfUser(user);
        userStorage.change(user);
        log.trace("Пользователь изменен");
    }

    public Collection<User> getUsersList() {
        return userStorage.getUserList().values();
    }

    public User getUser(int id) {
        return userStorage.getUserById(id);
    }

    public void addInFriend(int id, int friendId) {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        user.addFriend(friend);
        //friend.addFriend(user);// падает программа
    }

    public void deleteInFriend(int id, int friendId) {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        user.deleteFriend(friend);
        friend.deleteFriend(user);
    }

    public Collection<User> getFriends(int id) {
        return userStorage.getUserById(id).getFriends();
    }

    public Collection<User> getFriends(int id, int otherId) {
        User user = userStorage.getUserById(id);
        User otherUser = userStorage.getUserById(otherId);

        List<User> generalFriends = new ArrayList<>();

        for(User tempUser : user.getFriends()){
            for(User otherTempUser : otherUser.getFriends()){
                if(tempUser.equals(otherTempUser)) {
                    generalFriends.add(tempUser);
                }
            }
        }
        return generalFriends;
    }

    private void validateOfUser(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.trace("Некорректно указан email");
            throw new ValidationException("Некорректно указан email");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.trace("Некорректно указан login");
            throw new ValidationException("Некорректно указан login");
        }
        if (user.getName() == null || user.getName().length() == 0) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.trace("Некорректно указана дата рождения");
            throw new ValidationException("Некорректно указана дата рождения");
        }
    }
}
