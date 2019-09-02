package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.User;

public interface UsersDao {
  public List<User> getAllUsers();

  public List<User> getOnlineUsers();

  public List<User> getFriendsList(String userName);

  public int createUser(User user);
}