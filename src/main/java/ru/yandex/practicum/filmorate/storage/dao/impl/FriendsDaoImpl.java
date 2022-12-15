package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.FriendsDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class FriendsDaoImpl implements FriendsDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Integer> getAllFriends(int id) {
        List<Integer> friendsList = new ArrayList<>();
        SqlRowSet friendsRow =  jdbcTemplate.queryForRowSet("SELECT ID_FRIEND FROM PUBLIC.FRIENDS WHERE ID_USER = ?" +
                "AND STATUS_ID = 1", id);
        while(friendsRow.next()) {
            friendsList.add(friendsRow.getInt("ID_FRIEND"));
        }
        return friendsList;
    }

    @Override
    public Collection<Integer> getCommonFriends(int id, int otherId) {
        List<Integer> friendsList = new ArrayList<>();
        SqlRowSet commonFriendsRow = jdbcTemplate.queryForRowSet("SELECT U1.ID_USER, U1.ID_FRIEND AS COMMON_FRIENDS," +
                "U2.ID_USER FROM FRIENDS AS U1 JOIN FRIENDS AS U2 ON U2.ID_FRIEND=U1.ID_FRIEND " +
                "WHERE U1.ID_USER = ? AND U2.ID_USER = ?", id, otherId);
        while(commonFriendsRow.next()) {
            friendsList.add(commonFriendsRow.getInt("COMMON_FRIENDS"));
        }
        return friendsList;
    }

    @Override
    public void addInFriend(int id, int friendId) {
        String sqlQuery = "INSERT INTO PUBLIC.FRIENDS (ID_USER, ID_FRIEND, STATUS_ID)" +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                id, friendId, 1);
        jdbcTemplate.update(sqlQuery,
                friendId, id, 2);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        jdbcTemplate.update("DELETE FROM PUBLIC.FRIENDS " +
                "WHERE ID_USER = ? AND ID_FRIEND = ? AND STATUS_ID = 1", id,friendId);
    }
}
