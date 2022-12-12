package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UsersDao;
import ru.yandex.practicum.filmorate.storage.dao.impl.UserDaoImpl;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    UsersDao usersDao;

    @Autowired
    public InMemoryUserStorage(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    private final Map<Integer, User> userList = new HashMap<>();

    @Override
    public Map<Integer, User> getUserList() {
        return userList;
    }

//    @Override
//    public void add(User user) {
//        user.setId(id);
//        userList.put(id,user);
//        id++;
//    }

    @Override
    public void add(User user) {
        usersDao.create(user);
    }


    @Override
    public void change(User user) {
        usersDao.change(user);
//        validateId(user.getId());
//        if (!userList.containsValue(user)) {
//            userList.put(user.getId(), user);
//        } else {
//            throw new ValidationException("duplicate found in database");
//        }
    }

    @Override
    public User getUserById(int id) {
        validateId(id);
        return userList.get(id);
    }

    @Override
    public void validateId (int id) {
        if (!userList.containsKey(id)) {
            throw new ValidationException("User not found");
        }
    }
}
