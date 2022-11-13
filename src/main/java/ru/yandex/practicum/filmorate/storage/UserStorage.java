package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {
    void add(User user);

    void change(User user);

    Map<Integer, User> getUserList();

    void delete();

    void search();

    void validateId (int id);
}
