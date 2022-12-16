package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class MpaService {

    MpaDao mpaDao;

    @Autowired
    public MpaService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    public Optional<Mpa> getMpaById(int id) {
        validateId(id);
        Optional<Mpa> mpa = mpaDao.getMpaById(id);
        log.info("Return Mpa by Id successfully");
        return mpa;
    }

    public Collection<Mpa> getMpaList() {
        Collection<Mpa> collectionMpa = mpaDao.getMpaList();
        log.info("Return Mpa list successfully");
        return collectionMpa;
    }

    public void validateId(int id) {
        if (mpaDao.getMpaById(id).isEmpty()) {
            throw new ValidationException("Mpa not found");
        }
    }
}
