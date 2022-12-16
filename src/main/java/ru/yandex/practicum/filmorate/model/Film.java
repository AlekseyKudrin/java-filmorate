package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
    int rate;
    Mpa mpa;
    LinkedHashSet<Genre> genres;
}
