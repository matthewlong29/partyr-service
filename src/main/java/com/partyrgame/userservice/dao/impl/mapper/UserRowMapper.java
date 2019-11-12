package com.partyrgame.userservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partyrgame.userservice.model.OnlineStatus;
import com.partyrgame.userservice.model.PartyrUser;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<PartyrUser> {
  /**
   * mapRow.
   */
  @Override
  public PartyrUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    PartyrUser user = new PartyrUser();
    user.setUsername(resultSet.getString("username"));
    user.setEmail(resultSet.getString("email"));
    user.setFirstName(resultSet.getString("first_name"));
    user.setLastName(resultSet.getString("last_name"));
    user.setProfileImageURL(resultSet.getString("profile_image_url"));
    user.setUserHash(resultSet.getString("user_hash"));
    user.setJoinedDate(resultSet.getTimestamp("joined_date"));
    user.setOnlineStatus(OnlineStatus.valueOf(resultSet.getString("online_status")));
    user.setThemeName(resultSet.getString("theme_name"));
    user.setAge(resultSet.getInt("age"));
    user.setCountry(resultSet.getString("country"));

    return user;
  }
}
