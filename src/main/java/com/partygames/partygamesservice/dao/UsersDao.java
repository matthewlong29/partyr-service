package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.User;

public interface UsersDao {
  public List<User> getAllUsers();

  public List<User> searchForUsersByName(String text);

  public List<User> getOnlineUsers();

  public List<User> getBlockedList(String userName);

  public int blockUser(String currentUser, String userToBlock);

  public List<User> getFriendsList(String userName);

  public List<User> getOnlineFriendsList(String userName);
  
  public int addUser(String currentUser, String userToAdd);

  public int createUser(User user);

  public int chooseTheme(String userToUpdate, int themeID);

  public int changePassword(String userToUpdate, String newPassword);
}