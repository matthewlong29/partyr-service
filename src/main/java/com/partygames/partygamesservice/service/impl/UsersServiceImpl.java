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
   * getAllUsers returns all users and optionally only online users.
   */
  public List<User> getAllUsers(boolean onlineOnly, String queryString) {
    log.info("QUERY STRING: " + queryString);
    if (onlineOnly) {
      if (!queryString.isEmpty()) {
        log.info("querying for: " + queryString);
      }

      return usersDao.getOnlineUsers();
    }

    return usersDao.getAllUsers();
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
   * searchForUsersByName.
   */
  public List<User> searchForUsersByName(String text) {
    return usersDao.searchForUsersByName(text);
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