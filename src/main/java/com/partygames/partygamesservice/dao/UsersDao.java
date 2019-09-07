package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.Relationship;
import com.partygames.partygamesservice.model.Relationships;
import com.partygames.partygamesservice.model.User;

public interface UsersDao {
  public List<User> serchForOnlineUsersReadyToPlayContaining(String queryString);

  public List<User> getOnlineUsersReadyToPlay();

  public List<User> searchForOnlineUsersContaining(String queryString);

  public List<User> getOnlineUsers();

  public List<User> searchForAllUsersContaining(String queryString);

  public List<User> getAllUsers();

  public Relationships getBlockedList(String userName);

  public Relationships getFriendsList(String userName);

  public Relationships getOnlineFriendsList(String userName);

  public int createRelationship(Relationship newRelationship);

  public int createUser(User user);

  public int chooseTheme(String userToUpdate, int themeID);

  public int changePassword(String userToUpdate, String newPassword);
}