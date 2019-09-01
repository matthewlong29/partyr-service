package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.User;

public interface PartyGamesDao {
  public List<User> getAllUsers();
}