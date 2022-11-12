package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    public UserService userService = new UserService();

    @PostMapping
    public User createUser(@RequestBody User user) {
        userService.create(user);
        log.info("Пользователь создан успешно");
        return user;
    }

    @PutMapping
    public User changeUser(@RequestBody User user) {
        userService.change(user);
        log.info("Позьзователь успешно изменен");
        return user;
    }

    @GetMapping
    public Collection<User> getUserList() {
        log.info("Возврат списка пользователей");
        return userService.getUsersList();
    }
}
