package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        log.info("User successfully created");
    }

    public void change(User user) {
        validateOfUser(user);
        userStorage.change(user);
        log.info("User successfully change");
    }

    public Collection<User> getUsersList() {
        Collection<User> collectionUser = userStorage.getUserList().values();
        log.info("Return user list successfully");
        return collectionUser;
    }

    public User getUser(int id) {
        User user = userStorage.getUserById(id);
        log.info("Return user by Id successfully");
        return user;
    }

    public void addInFriend(int id, int friendId) {
//        User user = userStorage.getUserById(id);
//        User friend = userStorage.getUserById(friendId);
//        user.addFriend(friend.getId());
//        friend.addFriend(user.getId());
//        log.info("Friend add successfully");
    }

    public void deleteInFriend(int id, int friendId) {
//        User user = userStorage.getUserById(id);
//        User friend = userStorage.getUserById(friendId);
//        user.deleteFriend(friend.getId());
//        friend.deleteFriend(user.getId());
//        log.info("Friend delete successfully");
    }

    public Collection<User> getFriends(int id) {
        List <User> listFriends = new ArrayList<>();
//        Set<Integer> listIdFriends = userStorage.getUserById(id).getFriends();
//        for (Integer user : listIdFriends) {
//            for (Integer userId : userStorage.getUserList().keySet()) {
//                if (user.equals(userId)) {
//                    listFriends.add(userStorage.getUserList().get(userId));
//                }
//            }
//        }
//        log.info("Return list friends successfully");
        return listFriends;
    }

    public Collection<User> getFriends(int id, int otherId) {
        User user = userStorage.getUserById(id);
        User otherUser = userStorage.getUserById(otherId);

        List<Integer> generalFriends = new ArrayList<>();
        List<User> userList = new ArrayList<>();
//
//        for(Integer userId : user.getFriends()){
//            for(Integer otherUserId : otherUser.getFriends()){
//                if(userId.equals(otherUserId)) {
//                    generalFriends.add(userId);
//                }
//            }
//        }
//        for (Integer generalId : generalFriends) {
//            for (Integer userId : userStorage.getUserList().keySet()) {
//                if (generalId.equals(userId)) {
//                    userList.add(userStorage.getUserList().get(userId));
//                }
//            }
//        }
//        log.info("Return general list friends successfully");
        return userList;
    }

    private void validateOfUser(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.warn("Error in email: email cannot be empty and must contains character @");
            throw new IncorrectValueException("Email empty or not contains character @");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.warn("Error in login: login cannot be empty and not must contains character space");
            throw new IncorrectValueException("login cannot be empty or not must contains character space");
        }
        if (user.getName() == null || user.getName().length() == 0) {
            user.setName(user.getLogin());
            log.info("The user specified an empty name. Default name = " + user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Error in date birthday: date birthday can't be after time now");
            throw new IncorrectValueException("Date birthday can't be after time now");
        }
    }
}
