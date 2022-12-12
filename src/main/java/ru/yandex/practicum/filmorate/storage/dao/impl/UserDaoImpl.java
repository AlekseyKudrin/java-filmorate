package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UsersDao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
public class UserDaoImpl implements UsersDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        String sqlQuery = "INSERT INTO PUBLIC.USERS (EMAIL, LOGIN, NAME, BIRTHDAY)" +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()));
    }

    @Override
    public void change(User user) {
        getUserById(user.getId());


    }

    @Override
    public Map<Integer, User> getUserList() {
        return null;
    }

    @Override
    public void validateId(int id) {

    }

    @Override
    public User getUserById(int id) {
        String sqlQuery = "select * from PUBLIC.USERS where ID = "+ id;
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sqlQuery);
        User user = new User(
                userRows.getInt("ID"),
                userRows.getString("EMAIL"),
                userRows.getString("LOGIN"),
                userRows.getString("NAME"),
                userRows.getDate("BIRTHDAY").toLocalDate()
        );

        return user;
//                jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
    }

/*    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }*/
}
