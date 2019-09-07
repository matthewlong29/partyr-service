package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.Relationship;
import com.partygames.partygamesservice.model.Relationships;
import com.partygames.partygamesservice.model.User;

public interface UsersService {
  public List<User> getAllUsers(boolean onlineOnly, boolean readyToPlay, String queryString);

  public Relationships getRelationships(String userName, String relationshipStatus, boolean onlineOnly);

  public int createRelationship(Relationship newRelationship);

  public int createUser(User user);

  public int chooseTheme(String userToUpdate, int themeID);

  public int changePassword(String userToUpdate, String newPassword);
}