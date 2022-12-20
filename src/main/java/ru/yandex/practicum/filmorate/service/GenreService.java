package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Collection<Genre> getGenresList() {
        Collection<Genre> collectionGenres = genreDao.getGenresList();
        log.info("Return Mpa list successfully");
        return collectionGenres;
    }

    public Optional<Genre> getGenreById(int id) {
        validateId(id);
        Optional<Genre> genre = genreDao.getGenreById(id);
        log.info("Return Genre by Id successfully");
        return genre;
    }

    public void validateId(int id) {
        if (genreDao.getGenreById(id).isEmpty()) {
            throw new ValidationException("Genre not found");
        }
    }
}
