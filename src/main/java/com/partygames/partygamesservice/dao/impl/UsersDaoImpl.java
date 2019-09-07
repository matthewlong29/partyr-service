package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.dao.impl.mapper.UserRowMapper;
import com.partygames.partygamesservice.model.FriendStatus;
import com.partygames.partygamesservice.model.OnlineStatus;
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
    String query = "select * from Users;";

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * getOnlineUsers.
   */
  public List<User> getOnlineUsers() {
    StringBuilder query = new StringBuilder();
    query.append("select * from Users where online_status = '");
    query.append(OnlineStatus.ONLINE);
    query.append("';");

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * getBlockedList.
   */
  public List<User> getBlockedList(String userName) {
    StringBuilder query = new StringBuilder();
    query.append(
        "select user_id, user_name, email, password, joined_date, online_status, theme_id, age, country from Users ");
    query.append("left join Relationships on (Relationships.related_name = Users.user_name) where relating_name = '");
    query.append(userName);
    query.append("' and relationship_type = '");
    query.append(FriendStatus.BLOCK);
    query.append("';");

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * blockUser
   */
  public int blockUser(String currentUser, String userToBlock) {
    StringBuilder query = new StringBuilder();
    query.append(
        "insert into `PartyGamesDatabase`.`Relationships` (`relating_name`, `related_name`, `relationship_type`) values ('");
    query.append(currentUser);
    query.append("', '");
    query.append(userToBlock);
    query.append("', '");
    query.append(FriendStatus.BLOCK);
    query.append("');");

    return jdbcTemplate.update(query.toString());
  }

  /**
   * getFriendsList.
   */
  public List<User> getFriendsList(String userName) {
    StringBuilder query = new StringBuilder();
    query.append(
        "select user_id, user_name, email, password, joined_date, online_status, theme_id, age, country from Users ");
    query.append("left join Relationships on (Relationships.related_name = Users.user_name) where relating_name = '");
    query.append(userName);
    query.append("' and relationship_type = '");
    query.append(FriendStatus.FRIEND);
    query.append("';");

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * getOnlineFriendsList.
   */
  public List<User> getOnlineFriendsList(String userName) {
    StringBuilder query = new StringBuilder();
    query.append(
        "select user_id, user_name, email, password, joined_date, online_status, theme_id, age, country from Users ");
    query.append("left join Relationships on (Relationships.related_name = Users.user_name) where relating_name = '");
    query.append(userName);
    query.append("' and relationship_type = '");
    query.append(FriendStatus.FRIEND);
    query.append("' and online_status = '");
    query.append(OnlineStatus.ONLINE);
    query.append("';");

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * addUser.
   */
  public int addUser(String currentUser, String userToAdd) {
    StringBuilder query = new StringBuilder();
    query.append(
        "insert into `PartyGamesDatabase`.`Relationships` (`relating_name`, `related_name`, `relationship_type`) values ('");
    query.append(currentUser);
    query.append("', '");
    query.append(userToAdd);
    query.append("', '");
    query.append(FriendStatus.FRIEND);
    query.append("');");

    return jdbcTemplate.update(query.toString());
  }

  /**
   * searchForUsersByName
   */
  public List<User> searchForUsersByName(String text) {
    StringBuilder query = new StringBuilder();
    query.append("select * from Users where user_name like '%");
    query.append(text);
    query.append("%';");

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

  /**
   * chooseTheme.
   */
  public int chooseTheme(String userToUpdate, int themeID) {
    StringBuilder query = new StringBuilder();
    query.append("update `PartyGamesDatabase`.`Users` set `theme_id` = ");
    query.append(themeID);
    query.append(" where `user_name` = '");
    query.append(userToUpdate);
    query.append("';");

    return jdbcTemplate.update(query.toString());
  }

  /**
   * changePassword.
   */
  public int changePassword(String userToUpdate, String newPassword) {
    StringBuilder query = new StringBuilder();
    query.append("update `PartyGamesDatabase`.`Users` set `password` = ");
    query.append(newPassword);
    query.append(" where `user_name` = '");
    query.append(userToUpdate);
    query.append("';");

    return jdbcTemplate.update(query.toString());
  }
}
