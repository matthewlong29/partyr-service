package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.model.users.PartyrUser;
import com.partygames.partygamesservice.model.relationships.Relationship;
import com.partygames.partygamesservice.model.relationships.RelationshipStatus;
import com.partygames.partygamesservice.model.relationships.Relationships;
import com.partygames.partygamesservice.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
  @Autowired
  UsersDao usersDao;

  /**
   * getCurrentUser.
   */
  public PartyrUser getCurrentUser(String email) {
    return usersDao.getUserByEmail(email);
  }

  /**
   * getAllUsers: returns all users and optionally: only online users, users ready
   * to play, and users with user name or email containing a passed in query
   * string value (no spaces are allowed in username and email). note: cannot be
   * ready to play and offline.
   */
  public List<PartyrUser> getAllUsers(boolean isOnlineOnly, String queryString) {
    if (isOnlineOnly && !queryString.isEmpty()) {
      log.info("GETTING ALL ONLINE USERS CONTAINING " + queryString);
      return usersDao.searchForOnlineUsersContaining(queryString);
    } else if (isOnlineOnly && queryString.isEmpty()) {
      log.info("GETTING ALL ONLINE USERS: ");
      return usersDao.getOnlineUsers();
    } else if (!isOnlineOnly && !queryString.isEmpty()) {
      log.info("GETTING ALL USERS CONTAINING " + queryString);
      return usersDao.searchForAllUsersContaining(queryString);
    } else {
      log.info("GETTING ALL USERS");
      return usersDao.getAllUsers();
    }
  }

  /**
   * getRelationships.
   */
  public Relationships getRelationships(String username, String relationshipStatus, boolean onlineOnly) {
    if (relationshipStatus.equalsIgnoreCase(RelationshipStatus.FRIEND.toString()) && onlineOnly) {
      log.info("GETTING ALL ONLINE FRIENDS OF: " + username);
      return usersDao.getOnlineFriendsList(username);
    } else if (relationshipStatus.equalsIgnoreCase(RelationshipStatus.FRIEND.toString()) && !onlineOnly) {
      log.info("GETTING ALL FRIENDS OF: " + username);
      return usersDao.getFriendsList(username);
    } else if (relationshipStatus.equalsIgnoreCase(RelationshipStatus.BLOCK.toString())) {
      log.info("GETTING ALL ACCOUNTS BLOCKED BY: " + username);
      return usersDao.getBlockedList(username);
    } else {
      log.info("GETTING ALL RELATIONSHIPS OF: " + username);

      Relationships relationships = new Relationships();
      relationships.setFriendsList(usersDao.getFriendsList(username).getFriendsList());
      relationships.setBlockedList(usersDao.getBlockedList(username).getBlockedList());

      return relationships;
    }
  }

  /**
   * createRelationship.
   */
  public int createRelationship(Relationship newRelationship) {
    return usersDao.createRelationship(newRelationship);
  }

  /**
   * createUser.
   */
  public int createUser(PartyrUser user) {
    return usersDao.createUser(user);
  }

  /**
   * selectTheme.
   */
  public int selectTheme(String userToUpdate, int themeID) {
    return usersDao.selectTheme(userToUpdate, themeID);
  }

  /**
   * selectUsername.
   */
  public int selectUsername(String userToUpdate, String username) {
    return usersDao.selectUsername(userToUpdate, username);
  }
}
