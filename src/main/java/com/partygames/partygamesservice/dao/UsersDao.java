package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.Relationship;
import com.partygames.partygamesservice.model.Relationships;
import com.partygames.partygamesservice.model.PartyrUser;

public interface UsersDao {
  public List<PartyrUser> serchForOnlineUsersReadyToPlayContaining(String queryString);

  public List<PartyrUser> getOnlineUsersReadyToPlay();

  public List<PartyrUser> searchForOnlineUsersContaining(String queryString);

  public List<PartyrUser> getOnlineUsers();

  public List<PartyrUser> searchForAllUsersContaining(String queryString);

  public List<PartyrUser> getAllUsers();

  public Relationships getBlockedList(String userName);

  public Relationships getFriendsList(String userName);

  public Relationships getOnlineFriendsList(String userName);

  public int createRelationship(Relationship newRelationship);

  public int createUser(PartyrUser user);

  public int chooseTheme(String userToUpdate, int themeID);

  public int changePassword(String userToUpdate, String newPassword);
}