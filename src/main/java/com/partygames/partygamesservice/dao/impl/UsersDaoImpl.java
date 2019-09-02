package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.dao.impl.mapper.UserRowMapper;
import com.partygames.partygamesservice.model.User;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDaoImpl implements UsersDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  UserRowMapper userRowMapper;

  @Autowired
  PartyLogger log;

  /**
   * getAllUsers.
   */
  public List<User> getAllUsers() {
    String query = "select * from Users";

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * getOnlineUsers.
   */
  public List<User> getOnlineUsers() {
    String query = "select * from Users where online_status = 'ONLINE'";

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * getFriendsList.
   */
  public List<User> getFriendsList(String userName) {
    StringBuilder query = new StringBuilder();
    query.append("select user_id, user_name, email, password, joined_date, online_status, theme_id, age from Users ");
    query.append("left join Relationships on (Relationships.related_name = Users.user_name) where relating_name = '");
    query.append(userName);
    query.append("' and relationship_type = 'FRIEND';");

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * createUser.
   */
  public int createUser(User user) {
    StringBuilder query = new StringBuilder();
    query.append("insert into `PartyGamesDatabase`.`Users` (`user_name`, `email`, `age`, `password`) values ('");
    query.append(user.getUserName());
    query.append("','");
    query.append(user.getEmail());
    query.append("',");
    query.append(user.getAge());
    query.append(",'");
    query.append(user.getPassword());
    query.append("');");

    return jdbcTemplate.update(query.toString());
  }
}