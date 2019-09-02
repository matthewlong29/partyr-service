package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.User;

public interface UsersService {
  public List<User> getAllUsers();

  public List<User> getOnlineUsers();

  public List<User> getFriendsList(String userName);

  public int createUser(User user);
}