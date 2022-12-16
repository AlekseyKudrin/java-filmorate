package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Optional;

public interface MpaDao {
    Optional<Mpa> getMpaById(int id);

    Collection<Mpa> getMpaList();
}
