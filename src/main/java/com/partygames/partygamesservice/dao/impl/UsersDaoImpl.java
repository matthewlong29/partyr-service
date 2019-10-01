package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.dao.impl.mapper.UserRowMapper;
import com.partygames.partygamesservice.model.RelationshipStatus;
import com.partygames.partygamesservice.model.Relationships;
import com.partygames.partygamesservice.model.OnlineStatus;
import com.partygames.partygamesservice.model.ReadyStatus;
import com.partygames.partygamesservice.model.Relationship;
import com.partygames.partygamesservice.model.PartyrUser;
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

  /**
   * 
   */
  public List<PartyrUser> serchForOnlineUsersReadyToPlayContaining(String queryString) {
    StringBuilder query = new StringBuilder();
    query.append("select * from Users where ready_to_play_status = '");
    query.append(ReadyStatus.READY);
    query.append("' and (email like '%");
    query.append(queryString);
    query.append("%' or user_name like '%");
    query.append(queryString);
    query.append("%') order by user_name;");

    PartyLogger.query(query.toString());

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * 
   */
  public List<PartyrUser> getOnlineUsersReadyToPlay() {
    StringBuilder query = new StringBuilder();
    query.append("select * from Users where ready_to_play_status = '");
    query.append(ReadyStatus.READY);
    query.append("' order by user_name;");

    PartyLogger.query(query.toString());

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * 
   */
  public List<PartyrUser> searchForOnlineUsersContaining(String queryString) {
    StringBuilder query = new StringBuilder();
    query.append("select * from Users where online_status = '");
    query.append(OnlineStatus.ONLINE);
    query.append("' and (email like '%");
    query.append(queryString);
    query.append("%' or user_name like '%");
    query.append(queryString);
    query.append("%') order by user_name;");

    PartyLogger.query(query.toString());

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * 
   */
  public List<PartyrUser> getOnlineUsers() {
    StringBuilder query = new StringBuilder();
    query.append("select * from Users where online_status = '");
    query.append(OnlineStatus.ONLINE);
    query.append("' order by user_name;");

    PartyLogger.query(query.toString());

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * 
   */
  public List<PartyrUser> searchForAllUsersContaining(String queryString) {
    StringBuilder query = new StringBuilder();
    query.append("select * from Users where ");
    query.append("(email like '%");
    query.append(queryString);
    query.append("%' or user_name like '%");
    query.append(queryString);
    query.append("%') order by user_name;");

    PartyLogger.query(query.toString());

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * getAllUsers.
   */
  public List<PartyrUser> getAllUsers() {
    StringBuilder query = new StringBuilder();
    query.append("select * from Users order by online_status desc, user_name;");

    PartyLogger.query(query.toString());

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * getBlockedList.
   */
  public Relationships getBlockedList(String userName) {
    StringBuilder query = new StringBuilder();
    query.append(
        "select user_id, user_name, email, password, joined_date, online_status, theme_id, age, country from Users ");
    query.append("left join Relationships on (Relationships.related_name = Users.user_name) where relating_name = '");
    query.append(userName);
    query.append("' and relationship_type = '");
    query.append(RelationshipStatus.BLOCK);
    query.append("' order by user_name;");

    PartyLogger.query(query.toString());

    Relationships relationships = new Relationships();
    relationships.setBlockedList(jdbcTemplate.query(query.toString(), userRowMapper));

    return relationships;
  }

  /**
   * getFriendsList.
   */
  public Relationships getFriendsList(String userName) {
    StringBuilder query = new StringBuilder();
    query.append(
        "select user_id, user_name, email, password, joined_date, online_status, theme_id, age, country from Users ");
    query.append("left join Relationships on (Relationships.related_name = Users.user_name) where relating_name = '");
    query.append(userName);
    query.append("' and relationship_type = '");
    query.append(RelationshipStatus.FRIEND);
    query.append("' order by online_status desc, user_name;");

    PartyLogger.query(query.toString());

    Relationships relationships = new Relationships();
    relationships.setFriendsList(jdbcTemplate.query(query.toString(), userRowMapper));

    return relationships;
  }

  /**
   * getOnlineFriendsList.
   */
  public Relationships getOnlineFriendsList(String userName) {
    StringBuilder query = new StringBuilder();
    query.append(
        "select user_id, user_name, email, password, joined_date, online_status, theme_id, age, country from Users ");
    query.append("left join Relationships on (Relationships.related_name = Users.user_name) where relating_name = '");
    query.append(userName);
    query.append("' and relationship_type = '");
    query.append(RelationshipStatus.FRIEND);
    query.append("' and online_status = '");
    query.append(OnlineStatus.ONLINE);
    query.append("' order by user_name;");

    PartyLogger.query(query.toString());

    Relationships relationships = new Relationships();
    relationships.setFriendsList(jdbcTemplate.query(query.toString(), userRowMapper));

    return relationships;
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
    query.append(RelationshipStatus.BLOCK);
    query.append("');");

    PartyLogger.query(query.toString());

    return jdbcTemplate.update(query.toString());
  }

  /**
   * createRelationship.
   */
  public int createRelationship(Relationship newRelationship) {
    StringBuilder query = new StringBuilder();
    query.append(
        "insert into `PartyGamesDatabase`.`Relationships` (`relating_name`, `related_name`, `relationship_type`) values ('");
    query.append(newRelationship.getRelatingName());
    query.append("', '");
    query.append(newRelationship.getRelatedName());
    query.append("', '");
    query.append(newRelationship.getRelationshipStatus());
    query.append("');");

    PartyLogger.query(query.toString());

    return jdbcTemplate.update(query.toString());
  }

  /**
   * createUser.
   */
  public int createUser(PartyrUser user) {
    StringBuilder query = new StringBuilder();
    query.append(
        "insert into `PartyGamesDatabase`.`Users` (`user_name`, `email`, `country`, `age`, `password`) values ('");
    query.append(user.getUserName());
    query.append("', '");
    query.append(user.getEmail());
    query.append("', '");
    query.append(user.getCountry());
    query.append("', ");
    query.append(user.getAge());
    query.append(", '");
    query.append(user.getPassword());
    query.append("');");

    PartyLogger.query(query.toString());

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

    PartyLogger.query(query.toString());

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

    PartyLogger.query(query.toString());

    return jdbcTemplate.update(query.toString());
  }
}
