package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    int id = 1;
    private final Map<Integer, User> userList = new HashMap<>();

    @Override
    public Map<Integer, User> getUserList() {
        return userList;
    }

    @Override
    public void add(User user) {
        user.setId(id);
        userList.put(id,user);
        id++;
    }

    @Override
    public void change(User user) {
        validateId(user.getId());
        if (!userList.containsValue(user)) {
            userList.put(user.getId(), user);
        } else {
            throw new ValidationException("Найден дубль в БД");
        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void search() {

    }

    @Override
    public User getUserById(int id) {
        validateId(id);
        return userList.get(id);
    }

    @Override
    public void validateId (int id) {
        if (!userList.containsKey(id)) {
            throw new ValidationException("Пользователь не найден");
        }
    }
}
