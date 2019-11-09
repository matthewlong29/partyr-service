package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.users.PartyrUser;
import com.partygames.partygamesservice.model.relationships.Relationship;
import com.partygames.partygamesservice.model.relationships.Relationships;

public interface UsersService {
  public List<PartyrUser> getAllUsers(boolean onlineOnly, String queryString);

  public PartyrUser getCurrentUser(String email);

  public Relationships getRelationships(String username, String relationshipStatus, boolean onlineOnly);

  public int createRelationship(Relationship newRelationship);

  public int createUser(PartyrUser user);

  public int selectTheme(String userToUpdate, String chosenTheme);

  public List<String> getThemes();

  public int selectUsername(String userToUpdate, String username);
}