package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    int id = 1;
    private final Map<Integer, User> userList = new HashMap<>();

    public Map<Integer, User> getUserList() {
        return userList;
    }

    @Override
    public void add(User user) {
        userList.put(id,user);
        id++;
    }

    @Override
    public void change(User user) {
        if (!userList.containsValue(user)) {
            userList.put(user.getId(), user);
        } else {
            throw new ValidationException("Найден дубль в БД");
        }
    }
}
