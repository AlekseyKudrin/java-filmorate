package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UsersDao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        String sqlQuery = "UPDATE PUBLIC.USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE ID = ?";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId());
    }

    @Override
    public Collection<User> getUserList() {
        return jdbcTemplate.query("SELECT * FROM PUBLIC.USERS", this::mapRowToUsers);
    }

    @Override
    public void validateId(int id) {

    }

    @Override
    public Optional<User> getUserById(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from PUBLIC.USERS where ID = ?", id);
        if (userRows.next()) {
            User user = new User(
                    userRows.getInt("ID"),
                    userRows.getString("EMAIL"),
                    userRows.getString("LOGIN"),
                    userRows.getString("NAME"),
                    userRows.getDate("BIRTHDAY").toLocalDate()
            );
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<User> getUserById(Collection<Integer> friends) {
        List<User> friendsList = new ArrayList<>();
        for (int id : friends) {
            friendsList.add(getUserById(id).get());
        }
        return friendsList;
    }

    @Override
    public Optional<User> getUserAllFields(User user) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from PUBLIC.USERS where EMAIL = ? and LOGIN =?" +
                "and NAME = ? and BIRTHDAY = ?", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        if (userRows.next()) {
            User userFromDb = new User(
                    userRows.getInt("ID"),
                    userRows.getString("EMAIL"),
                    userRows.getString("LOGIN"),
                    userRows.getString("NAME"),
                    userRows.getDate("BIRTHDAY").toLocalDate()
            );
            return Optional.of(userFromDb);
        } else {
            return Optional.empty();
        }
    }

    private User mapRowToUsers(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String login = resultSet.getString("login");
        String name = resultSet.getString("name");
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
        return new User(id, email, login, name, birthday);
    }
}
