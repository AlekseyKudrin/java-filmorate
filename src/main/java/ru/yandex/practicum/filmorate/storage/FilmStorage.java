package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {

    void add(Film film);

    void change(Film film);

    Map<Integer, Film> getFilmList();

    Film getFilmById(int id);

    void validateId (int id);
}
