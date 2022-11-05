package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private int id = 1;
    public final Map<Integer, User> userList = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.trace ("Некорректно указан email");
            throw new ValidationException("Некорректно указан email");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.trace ("Некорректно указан login");
            throw new ValidationException("Некорректно указан login");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.trace("Некорректно указана дата рождения");
            throw new ValidationException("Некорректно указана дата рождения");
        }
        user.setId(id);
        userList.put(user.getId(), user);
        log.info("Пользователь создан успешно");
        id++;
        return user;
    }

    @PutMapping
    public User changeUser(@RequestBody User user) {
        if (userList.containsKey(user.getId())) {
            userList.put(user.getId(), user);
            log.info("Позьзователь успешно изменен");
            return user;
        } else {
            log.trace("Пользователь не изменен");
            throw new ValidationException("Пользователь не изменен");
        }
    }

    @GetMapping
    public Collection<User> getUserList() {
        log.info("Возврат списка пользователей");
        return userList.values();
    }
}
