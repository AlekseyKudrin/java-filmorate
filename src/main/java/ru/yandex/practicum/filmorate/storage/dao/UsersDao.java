package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Component
public interface UsersDao {

    void create(User user);

    void change(User user);

    Collection<User> getUserList();

    Optional<User> getUserById(int id);

    Collection<User> getUserById(Collection<Integer> friends);

    Optional<User> getUserAllFields(User user);
}
