package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    int id;
    String email;
    String login;
    String name;
    LocalDate birthday;
    public ArrayList<User> friends = new ArrayList<>();

    public void addFriend(User user) {
        friends.add(user);
    }

    public void deleteFriend(User user) {
        friends.remove(user);
    }
}