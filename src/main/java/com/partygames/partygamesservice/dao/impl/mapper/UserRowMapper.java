package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partygames.partygamesservice.model.OnlineStatus;
import com.partygames.partygamesservice.model.PartyrUser;

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
    user.setUserName("user_name");
    user.setEmail(resultSet.getString("email"));
    user.setJoinedDate(resultSet.getTimestamp("joined_date"));
    user.setOnlineStatus(OnlineStatus.valueOf(resultSet.getString("online_status")));
    user.setThemeID(resultSet.getInt("theme_id"));
    user.setAge(resultSet.getInt("age"));
    user.setCountry(resultSet.getString("country"));

    return user;
  }
}
