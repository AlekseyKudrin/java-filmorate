package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
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

    //Старый метод
    public void change2(User user) {
        if (userStorage.getUserList().containsKey(user.getId())) {
            validateOfUser(user);
            userStorage.change(user);
        } else {
            log.trace("Пользователь не изменен");
            throw new ValidationException("Пользователь не изменен");
        }

    }
    public void change(User user) {
            userStorage.validateId(user.getId());
            validateOfUser(user);
            userStorage.change(user);
            log.trace("Пользователь изменен");
    }

    public Collection<User> getUsersList() {
        return userStorage.getUserList().values();
    }

    public User getUser(int id) {
        return userStorage.getUserList().get(id);
    }

    public void addInFriend() {

    }

    public void deleteInFriend() {

    }

    public Collection<User> returnUserFriend() {
        return List.of();
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
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.trace("Некорректно указана дата рождения");
            throw new ValidationException("Некорректно указана дата рождения");
        }
    }
}
