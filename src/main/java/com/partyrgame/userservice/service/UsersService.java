package com.partyrgame.userservice.service;

import java.util.List;

import com.partyrgame.userservice.model.PartyrUser;
import com.partyrgame.userservice.model.Relationship;
import com.partyrgame.userservice.model.Relationships;

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