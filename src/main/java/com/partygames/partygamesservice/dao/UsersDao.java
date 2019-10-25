package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.relationships.Relationship;
import com.partygames.partygamesservice.model.relationships.Relationships;
import com.partygames.partygamesservice.model.users.PartyrUser;

public interface UsersDao {
  public PartyrUser getUserByEmail(String email);

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

  public int selectTheme(String userToUpdate, int themeID);

  public int selectUsername(String userToUpdate, String userName);
}