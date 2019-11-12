package com.partyrgame.userservice.dao;

import java.util.List;

import com.partyrgame.userservice.model.Relationship;
import com.partyrgame.userservice.model.Relationships;
import com.partyrgame.userservice.model.PartyrUser;

public interface UsersDao {
  public PartyrUser getUserByEmail(String email);

  public List<PartyrUser> searchForOnlineUsersContaining(String queryString);

  public List<PartyrUser> getOnlineUsers();

  public List<PartyrUser> searchForAllUsersContaining(String queryString);

  public List<PartyrUser> getAllUsers();

  public Relationships getBlockedList(String username);

  public Relationships getFriendsList(String username);

  public Relationships getOnlineFriendsList(String username);

  public int createRelationship(Relationship newRelationship);

  public int createUser(PartyrUser user);

  public int selectTheme(String userToUpdate, String themeName);

  public List<String> getThemes();

  public int selectUsername(String userToUpdate, String username);
}