package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.User;

public interface UsersService {
  public List<User> getAllUsers();

  public List<User> getOnlineUsers();

  public List<User> getFriendsList(String userName);

  public int addUser(String currentUser, String userToAdd);

  public List<User> getBlockedList(String userName);

  public int blockUser(String currentUser, String userToBlock);

  public List<User> searchForUsersByName(String text);

  public int createUser(User user);

  public int chooseTheme(String userToUpdate, int themeID);

  public int changePassword(String userToUpdate, String newPassword);
}