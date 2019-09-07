package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.model.User;
import com.partygames.partygamesservice.service.UsersService;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired
  UsersDao usersDao;

  @Autowired
  PartyLogger log;

  /**
   * getAllUsers: returns all users and optionally: only online users, users ready
   * to play, and users with user name or email containing a passed in query
   * string value (no spaces are allowed in username and email). note: cannot be
   * ready to play and offline.
   */
  public List<User> getAllUsers(boolean isOnlineOnly, boolean isReadyToPlay, String queryString) {
    if (isReadyToPlay && !queryString.isEmpty()) {
      log.info("GETTING ONLINE USERS THAT ARE READY TO PLAY CONTAINING " + queryString);
      return usersDao.serchForOnlineUsersReadyToPlayContaining(queryString);
    } else if (isReadyToPlay && queryString.isEmpty()) {
      log.info("GETTING ONLINE USERS THAT ARE READY TO PLAY");
      return usersDao.getOnlineUsersReadyToPlay();
    } else if (isOnlineOnly && !isReadyToPlay && !queryString.isEmpty()) {
      log.info("GETTING ALL ONLINE USERS CONTAINING " + queryString);
      return usersDao.searchForOnlineUsersContaining(queryString);
    } else if (isOnlineOnly && !isReadyToPlay && queryString.isEmpty()) {
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
   * getFriendsList.
   */
  public List<User> getFriendsList(String userName, boolean onlineOnly) {
    if (onlineOnly) {
      return usersDao.getOnlineFriendsList(userName);
    }

    return usersDao.getFriendsList(userName);
  }

  /**
   * addUser.
   */
  public int addUser(String currentUser, String userToAdd) {
    return usersDao.addUser(currentUser, userToAdd);
  }

  /**
   * getBlockedList.
   */
  public List<User> getBlockedList(String userName) {
    return usersDao.getBlockedList(userName);
  }

  /**
   * blockUser.
   */
  public int blockUser(String currentUser, String userToBlock) {
    return usersDao.blockUser(currentUser, userToBlock);
  }

  /**
   * createUser.
   */
  public int createUser(User user) {
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