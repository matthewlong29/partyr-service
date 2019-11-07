package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.dao.impl.mapper.UserRowMapper;
import com.partygames.partygamesservice.model.relationships.Relationship;
import com.partygames.partygamesservice.model.relationships.Relationships;
import com.partygames.partygamesservice.model.users.PartyrUser;

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

    try {
      return jdbcTemplate.query(query, userRowMapper).get(0);  
    } catch (Exception e) {
      log.error("unable to find user by email of {}; error: {}", email, e.getMessage());
    }

    return new PartyrUser(); // TODO throw exception and provide error status code instead?
  }


  /**
   * searchForOnlineUsersContaining.
   */
  public List<PartyrUser> searchForOnlineUsersContaining(String queryString) {
    String query = "CALL `partyrdb`.`get_online_users`('" + queryString + "');";
    log.info(query);

    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * getOnlineUsers.
   */
  public List<PartyrUser> getOnlineUsers() {
    String query = "CALL `partyrdb`.`get_online_users`('');";
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
   * createRelationship: returns the number of rows affected. If unable to create
   * a relationship then 0 is returned.
   */
  public int createRelationship(Relationship newRelationship) {
    String query = "CALL `partyrdb`.`create_relationship`('" + newRelationship.getRelatingEmail() + "', '"
        + newRelationship.getRelatedEmail() + "', '" + newRelationship.getRelationshipStatus() + "');";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to add user {}; error: {}", newRelationship.getRelatingEmail(), e.getMessage());
    }

    return 0;
  }

  /**
   * createUser: returns the number of rows affected. if unable to create user
   * then 0 is returned.
   */
  public int createUser(PartyrUser user) {
    // dont create a user without an email
    if (null == user.getEmail()) {
      log.warn("cannot create a user without an email address");
      return 0;
    }

    String query = "CALL `partyrdb`.`create_user`('" + user.getUserHash() + "', '" + user.getEmail() + "', '"
        + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getProfileImageURL() + "');";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to create user {}; error: {}", user.getEmail(), e.getMessage());
    }

    return 0;
  }

  /**
   * selectTheme: returns the number of rows affected. if unable to select a theme
   * for that user then 0 is returned.
   */
  public int selectTheme(String userToUpdate, int themeID) {
    String query = "CALL `partyrdb`.`select_theme`('" + userToUpdate + "', '" + themeID + "');";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to update theme for user {}; error: {}", userToUpdate, e.getMessage());
    }

    return 0;
  }

  /**
   * selectUserName: returns the number of rows affected. if unable to select a
   * username for that user then 0 is returned.
   */
  public int selectUsername(String userToUpdate, String userName) {
    String query = "CALL `partyrdb`.`select_username`('" + userToUpdate + "', '" + userName + "');";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to update username for user {}; error: {}", userToUpdate, e.getMessage());
    }

    return 0;
  }
}
