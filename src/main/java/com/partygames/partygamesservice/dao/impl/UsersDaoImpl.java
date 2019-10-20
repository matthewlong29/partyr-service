package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.dao.impl.mapper.UserRowMapper;
import com.partygames.partygamesservice.model.PartyrUser;
import com.partygames.partygamesservice.model.Relationship;
import com.partygames.partygamesservice.model.RelationshipStatus;
import com.partygames.partygamesservice.model.Relationships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UsersDaoImpl implements UsersDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  UserRowMapper userRowMapper;

  /**
   * getUserByEmail.
   */
  public PartyrUser getUserByEmail(String email) {
    String query = "CALL `partyrdb`.`get_user_by_email`('" + email + "');";
    log.info(query);

    return jdbcTemplate.query(query, userRowMapper).get(0);
  }

  /**
   * serchForOnlineUsersReadyToPlayContaining.
   */
  public List<PartyrUser> serchForOnlineUsersReadyToPlayContaining(String queryString) {
    String query = "CALL `partyrdb`.`get_users_ready_to_play`('" + queryString + "');";
    log.info(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * getOnlineUsersReadyToPlay.
   */
  public List<PartyrUser> getOnlineUsersReadyToPlay() {
    String query = "CALL `partyrdb`.`get_users_ready_to_play`('');";
    log.info(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * searchForOnlineUsersContaining.
   */
  public List<PartyrUser> searchForOnlineUsersContaining(String queryString) {
    String query = "CALL `partyrdb`.`get_users_online`('" + queryString + "');";
    log.info(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * getOnlineUsers.
   */
  public List<PartyrUser> getOnlineUsers() {
    String query = "CALL `partyrdb`.`get_users_online`('');";
    log.info(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * searchForAllUsersContaining.
   */
  public List<PartyrUser> searchForAllUsersContaining(String queryString) {
    String query = "CALL `partyrdb`.`get_users`('" + queryString + "');";
    log.info(query);

    return jdbcTemplate.query(query.toString(), userRowMapper);
  }

  /**
   * getAllUsers.
   */
  public List<PartyrUser> getAllUsers() {
    String query = "CALL `partyrdb`.`get_users`('');";
    log.info(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * getBlockedList.
   */
  public Relationships getBlockedList(String userName) {
    String query = "CALL `partyrdb`.`get_relationships`('BLOCK', '" + userName + "');";
    log.info(query);

    Relationships relationships = new Relationships();
    relationships.setBlockedList(jdbcTemplate.query(query, userRowMapper));

    return relationships;
  }

  /**
   * getFriendsList.
   */
  public Relationships getFriendsList(String userName) {
    String query = "CALL `partyrdb`.`get_relationships`('FRIEND', '" + userName + "');";
    log.info(query);

    Relationships relationships = new Relationships();
    relationships.setFriendsList(jdbcTemplate.query(query, userRowMapper));

    return relationships;
  }

  /**
   * getOnlineFriendsList.
   */
  public Relationships getOnlineFriendsList(String email) {
    String query = "CALL `partyrdb`.`get_online_friends`('" + email + "');";
    log.info(query);

    Relationships relationships = new Relationships();
    relationships.setFriendsList(jdbcTemplate.query(query, userRowMapper));

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

    log.info(query.toString());

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

    log.info(query.toString());

    return jdbcTemplate.update(query.toString());
  }

  /**
   * createUserIfNotExist
   */
  public int createUserIfNotExist(PartyrUser user) {
    String query = "CALL `partyrdb`.`create_user`('" + user.getUserHash() + "', '" + user.getEmail() + "', '"
        + user.getFirstName().toString() + "', '" + user.getLastName() + "', '" + user.getProfileImageURL() + "');";
    log.info(query);

    return jdbcTemplate.update(query);
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

    log.info(query.toString());

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

    log.info(query.toString());

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

    log.info(query.toString());

    return jdbcTemplate.update(query.toString());
  }
}
