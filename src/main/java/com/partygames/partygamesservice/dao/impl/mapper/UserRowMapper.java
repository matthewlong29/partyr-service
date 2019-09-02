package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partygames.partygamesservice.model.OnlineStatus;
import com.partygames.partygamesservice.model.User;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<User> {
  @Override
  public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    User user = new User();
    user.setUserName(resultSet.getString("user_name"));
    user.setEmail(resultSet.getString("email"));
    user.setPassword(resultSet.getString("password"));
    user.setJoinedDate(resultSet.getTimestamp("joined_date"));
    user.setOnlineStatus(OnlineStatus.valueOf(resultSet.getString("online_status")));
    user.setThemeID(resultSet.getInt("theme_id"));
    user.setAge(resultSet.getInt("age"));

    return user;
  }
}