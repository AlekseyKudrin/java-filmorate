package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    private int id = 1;
    private final Map<Integer, Film> filmList = new HashMap<>();

    public Map<Integer, Film> getFilmList() {
        return filmList;
    }

    @Override
    public void add(Film film) {
        filmList.put(id,film);
        id++;
    }

    @Override
    public void change(Film film) {
        if (!filmList.containsValue(film)) {
            filmList.put(film.getId(), film);
        } else {
            throw new ValidationException("Найден дубль в БД");
        }
    }
}
