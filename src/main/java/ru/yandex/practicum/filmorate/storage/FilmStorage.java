package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    void create(Film film);

    void change(Film film);

    Optional<Film> getFilm(Film film);

    Collection<Film> getFilmList();

    Optional<Film> getFilmById(int id);

    Collection<Film> getPopFilms(int count);

    void validateId (int id);

    void addLike(int id, int userId);
}
