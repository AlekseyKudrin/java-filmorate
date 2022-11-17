package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void create(User user) {
        validateOfUser(user);
        userStorage.add(user);
    }

    public void change(User user) {
        validateOfUser(user);
        userStorage.change(user);
        log.trace("Пользователь изменен");
    }

    public Collection<User> getUsersList() {
        return userStorage.getUserList().values();
    }

    public User getUser(int id) {
        return userStorage.getUserById(id);
    }

    public void addInFriend(int id, int friendId) {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        user.addFriend(friend.getId());
        friend.addFriend(user.getId());
    }

    public void deleteInFriend(int id, int friendId) {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        user.deleteFriend(friend.getId());
        friend.deleteFriend(user.getId());
    }

    public Collection<User> getFriends(int id) {
        Map<Integer, User> listFriends = new HashMap<>();
        Set<Integer> listIdFriends = userStorage.getUserById(id).getFriends();
        for (Integer user : listIdFriends) {
            for (Integer userId : userStorage.getUserList().keySet()) {
                if (user.equals(userId)) {
                    listFriends.put(user, userStorage.getUserList().get(userId));
                }
            }
        }
        return listFriends.values();
    }

    public Collection<User> getFriends(int id, int otherId) {
        User user = userStorage.getUserById(id);
        User otherUser = userStorage.getUserById(otherId);

        Map<Integer, User> listFriends = new HashMap<>();
        List<Integer> generalFriends = new ArrayList<>();

        for(Integer userId : user.getFriends()){
            for(Integer otherTempUser : otherUser.getFriends()){
                if(userId.equals(otherTempUser)) {
                    generalFriends.add(userId);
                }
            }
        }
        for (Integer generalId : generalFriends) {
            for (Integer userId : userStorage.getUserList().keySet()) {
                if (generalId.equals(userId)) {
                    listFriends.put(generalId, userStorage.getUserList().get(userId));
                }
            }
        }
        return listFriends.values();
    }

    private void validateOfUser(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.trace("Некорректно указан email");
            throw new IncorrectValueException("Некорректно указан email");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.trace("Некорректно указан login");
            throw new IncorrectValueException("Некорректно указан login");
        }
        if (user.getName() == null || user.getName().length() == 0) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.trace("Некорректно указана дата рождения");
            throw new IncorrectValueException("Некорректно указана дата рождения");
        }
    }
}
