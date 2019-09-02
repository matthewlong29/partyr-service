package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.model.User;
import com.partygames.partygamesservice.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired
  UsersDao usersDao;

  /**
   * getAllUsers.
   */
  public List<User> getAllUsers() {
    return usersDao.getAllUsers();
  }

  /**
   * getOnlineUsers.
   */
  public List<User> getOnlineUsers() {
    return usersDao.getOnlineUsers();
  }

  /**
   * getFriendsList.
   */
  public List<User> getFriendsList(String userName) {
    return usersDao.getFriendsList(userName);
  }

  /**
   * createUser.
   */
  public int createUser(User user) {
    return usersDao.createUser(user);
  }
}