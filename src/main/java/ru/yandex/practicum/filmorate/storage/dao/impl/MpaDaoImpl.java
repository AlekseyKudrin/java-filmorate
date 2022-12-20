package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
public class MpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Mpa> getMpaById(int id) {
        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet("SELECT * FROM PUBLIC.MPA WHERE ID = ?", id);
        if (mpaRow.next()) {
            Mpa mpa = new Mpa(
                    mpaRow.getInt("ID"),
                    mpaRow.getString("NAME")
            );
            return Optional.of(mpa);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Mpa> getMpaList() {
        return jdbcTemplate.query("SELECT * FROM PUBLIC.MPA", this::mapRowToMpa);
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("NAME");
        return new Mpa(id, name);
    }

}
