package com.partygames.partygamesservice.dao.impl;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.dao.impl.mapper.UserRowMapper;
import com.partygames.partygamesservice.model.OnlineStatus;
import com.partygames.partygamesservice.model.PartyrUser;
import com.partygames.partygamesservice.model.Relationship;
import com.partygames.partygamesservice.model.RelationshipStatus;
import com.partygames.partygamesservice.model.Relationships;
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
    String query = "CALL `partyrdb`.`get_users_ready_to_play`('" + queryString + "');";
    PartyLogger.query(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * 
   */
  public List<PartyrUser> getOnlineUsersReadyToPlay() {
    String query = "CALL `partyrdb`.`get_users_ready_to_play`('');";
    PartyLogger.query(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * 
   */
  public List<PartyrUser> searchForOnlineUsersContaining(String queryString) {
    String query = "CALL `partyrdb`.`get_users_online`('" + queryString + "');";
    PartyLogger.query(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * 
   */
  public List<PartyrUser> getOnlineUsers() {
    String query = "CALL `partyrdb`.`get_users_online`('');";
    PartyLogger.query(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * 
   */
  public List<PartyrUser> searchForAllUsersContaining(String queryString) {
    String query = "CALL `partyrdb`.`get_all_users`('" + queryString + "');";
    PartyLogger.query(query);

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * getAllUsers.
   */
  public List<PartyrUser> getAllUsers() {
    String query = "CALL `partyrdb`.`get_all_users`('');";
    PartyLogger.query(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * getBlockedList.
   */
  public Relationships getBlockedList(String userName) {
    String query = "CALL `partyrdb`.`get_relationships`('BLOCK', '" + userName + "');";
    PartyLogger.query(query);

    Relationships relationships = new Relationships();
    relationships.setBlockedList(jdbcTemplate.query(query, userRowMapper));

    return relationships;
  }

  /**
   * getFriendsList.
   */
  public Relationships getFriendsList(String userName) {
    String query = "CALL `partyrdb`.`get_relationships`('FRIEND', '" + userName + "');";
    PartyLogger.query(query);

    Relationships relationships = new Relationships();
    relationships.setFriendsList(jdbcTemplate.query(query, userRowMapper));

    return relationships;
  }

  /**
   * getOnlineFriendsList.
   */
  public Relationships getOnlineFriendsList(String userName) {
    StringBuilder query = new StringBuilder();
    query.append(
        "select user_id, user_hash, user_name, first_name, last_name, email, profile_image_url, joined_date, online_status, ready_to_play_status, theme_id, age, country from partyr_users ");
    query.append(
        "left join Relationships on (Relationships.related_email = partyr_users.email) where relating_email = '");
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
        "insert into `PartyGamesDatabase`.`Relationships` (`relating_email`, `related_email`, `relationship_type`) values ('");
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
        "insert into `PartyGamesDatabase`.`Relationships` (`relating_email`, `related_email`, `relationship_type`) values ('");
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
   * createUserIfNotExist
   */
  public int createUserIfNotExist(PartyrUser user) {
    String insertCols = "user_hash, email, first_name, last_name, profile_image_url, joined_date";
    StringBuilder sql = new StringBuilder();
    sql.append("insert into PartyGamesDatabase.partyr_users (");
    sql.append(insertCols);
    sql.append(") values(?, ?, ?, ?, ?, now()) on duplicate key update user_id = user_id");
    String ts = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
    return jdbcTemplate.update(sql.toString(), user.getUserHash(), user.getEmail(), user.getFirstName(),
        user.getLastName(), user.getProfileImageURL());
  }

  /**
   * createUser.
   */
  public int createUser(PartyrUser user) {
    StringBuilder query = new StringBuilder();
    query.append(
        "insert into `PartyGamesDatabase`.`partyr_users` (`user_name`, `email`, `country`, `age`, `password`) values ('");
    query.append(user.getUserName());
    query.append("', '");
    query.append(user.getEmail());
    query.append("', '");
    query.append(user.getCountry());
    query.append("', ");
    query.append(user.getAge());

    PartyLogger.query(query.toString());

    return jdbcTemplate.update(query.toString());
  }

  /**
   * chooseTheme.
   */
  public int chooseTheme(String userToUpdate, int themeID) {
    StringBuilder query = new StringBuilder();
    query.append("update `PartyGamesDatabase`.`partyr_users` set `theme_id` = ");
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
    query.append("update `PartyGamesDatabase`.`partyr_users` set `password` = ");
    query.append(newPassword);
    query.append(" where `user_name` = '");
    query.append(userToUpdate);
    query.append("';");

    PartyLogger.query(query.toString());

    return jdbcTemplate.update(query.toString());
  }
}
