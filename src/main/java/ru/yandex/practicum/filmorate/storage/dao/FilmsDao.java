package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmsDao {

    void create(Film film);

    void change(Film film);

    Collection<Film> getFilmList();

    Optional<Film> getFilmById(int id);

    Optional<Film> getFilmAllFields(Film film);
}
