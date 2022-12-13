package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
//@Builder
//@FieldDefaults(level = AccessLevel.PUBLIC)
public class User {
    public int id;
    public String email;
    public String login;
    public String name;
    public LocalDate birthday;
}