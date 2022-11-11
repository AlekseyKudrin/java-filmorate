package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    void add(User user);

    void change(User user);
}
