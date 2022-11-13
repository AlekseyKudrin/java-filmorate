package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @PutMapping("{id}/friends/{friendId}")
    public void addInFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Друг добавлен");
        userService.addInFriend(id, friendId);
    }

    @GetMapping
    public Collection<User> getUserList() {
        log.info("Возврат списка пользователей");
        return userService.getUsersList();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        log.info("Данные о пользователе переданы");
        return userService.getUser(id);
    }

    @GetMapping("{id}/friends")
    public Collection<User> getAllFriends(@PathVariable int id) {
        log.info("Список друзей возвращен");
        return userService.getFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> getGeneralFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Список общих друзей возвращен");
        return userService.getFriends(id, otherId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteInFriend (@PathVariable int id, @PathVariable int friendId) {
        log.info("Друг удален");
        userService.deleteInFriend(id, friendId);
    }
}
