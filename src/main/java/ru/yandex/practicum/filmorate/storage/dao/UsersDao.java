package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Map;

@Component
public interface UsersDao {

    void create(User user);

    void change(User user);

    Map<Integer, User> getUserList();

    void validateId (int id);

    User getUserById(int id);

}
