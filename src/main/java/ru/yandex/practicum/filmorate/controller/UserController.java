package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Optional<User> createUser(@RequestBody User user) {
        log.info("Creating user");
        return userService.create(user);
    }

    @PutMapping
    public Optional<User> changeUser(@RequestBody User user) {
        log.info("User change");
        return userService.change(user);
    }

    @PutMapping("{id}/friends/{friendId}")
    public void addInFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Adding a friend");
        userService.addInFriend(id, friendId);
    }

    @GetMapping
    public Collection<User> getUserList() {
        log.info("Return user list");
        return userService.getUsersList();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable int id) {
        log.info("Return user by Id");
        return userService.getUser(id);
    }

    @GetMapping("{id}/friends")
    public Collection<User> getAllFriends(@PathVariable int id) {
        log.info("Return list friends");
        return userService.getFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Return general list friends");
        return userService.getFriends(id, otherId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteInFriend (@PathVariable int id, @PathVariable int friendId) {
        log.info("Deleting friend");
        userService.deleteFriend(id, friendId);
    }
}
