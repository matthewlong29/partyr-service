package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.model.PartyrUser;
import com.partygames.partygamesservice.model.Relationship;
import com.partygames.partygamesservice.model.RelationshipStatus;
import com.partygames.partygamesservice.model.Relationships;
import com.partygames.partygamesservice.service.UsersService;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired
  UsersDao usersDao;

  /**
   * getAllUsers: returns all users and optionally: only online users, users ready
   * to play, and users with user name or email containing a passed in query
   * string value (no spaces are allowed in username and email). note: cannot be
   * ready to play and offline.
   */
  public List<PartyrUser> getAllUsers(boolean isOnlineOnly, boolean isReadyToPlay, String queryString) {
    if (isReadyToPlay && !queryString.isEmpty()) {
      PartyLogger.info("GETTING ONLINE USERS THAT ARE READY TO PLAY CONTAINING " + queryString);
      return usersDao.serchForOnlineUsersReadyToPlayContaining(queryString);
    } else if (isReadyToPlay && queryString.isEmpty()) {
      PartyLogger.info("GETTING ONLINE USERS THAT ARE READY TO PLAY");
      return usersDao.getOnlineUsersReadyToPlay();
    } else if (isOnlineOnly && !isReadyToPlay && !queryString.isEmpty()) {
      PartyLogger.info("GETTING ALL ONLINE USERS CONTAINING " + queryString);
      return usersDao.searchForOnlineUsersContaining(queryString);
    } else if (isOnlineOnly && !isReadyToPlay && queryString.isEmpty()) {
      PartyLogger.info("GETTING ALL ONLINE USERS: ");
      return usersDao.getOnlineUsers();
    } else if (!isOnlineOnly && !queryString.isEmpty()) {
      PartyLogger.info("GETTING ALL USERS CONTAINING " + queryString);
      return usersDao.searchForAllUsersContaining(queryString);
    } else {
      PartyLogger.info("GETTING ALL USERS");
      return usersDao.getAllUsers();
    }
  }

  /**
   * getRelationships.
   */
  public Relationships getRelationships(String userName, String relationshipStatus, boolean onlineOnly) {
    if (relationshipStatus.equals(RelationshipStatus.FRIEND.toString()) && onlineOnly) {
      PartyLogger.info("GETTING ALL ONLINE FRIENDS OF: " + userName);
      return usersDao.getOnlineFriendsList(userName);
    } else if (relationshipStatus.equals(RelationshipStatus.FRIEND.toString()) && !onlineOnly) {
      PartyLogger.info("GETTING ALL FRIENDS OF: " + userName);
      return usersDao.getFriendsList(userName);
    } else if (relationshipStatus.equals(RelationshipStatus.BLOCK.toString())) {
      PartyLogger.info("GETTING ALL ACCOUNTS BLOCKED BY: " + userName);
      return usersDao.getBlockedList(userName);
    } else {
      PartyLogger.info("GETTING ALL RELATIONSHIPS OF: " + userName);

      Relationships relationships = new Relationships();
      relationships.setFriendsList(usersDao.getFriendsList(userName).getFriendsList());
      relationships.setBlockedList(usersDao.getBlockedList(userName).getBlockedList());

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
   * chooseTheme.
   */
  public int chooseTheme(String userToUpdate, int themeID) {
    return usersDao.chooseTheme(userToUpdate, themeID);
  }

  /**
   * changePassword.
   */
  public int changePassword(String userToUpdate, String newPassword) {
    return usersDao.changePassword(userToUpdate, newPassword);
  }
}
