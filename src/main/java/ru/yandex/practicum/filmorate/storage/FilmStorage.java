package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    void create(Film film);

    void change(Film film);

    Optional<Film> getFilm(Film film);

    Collection<Film> getFilmList();

    Film getFilmById(int id);

    void validateId (int id);
}
