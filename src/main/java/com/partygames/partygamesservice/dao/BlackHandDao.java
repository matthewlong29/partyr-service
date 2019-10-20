package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.BlackHandRole;

public interface BlackHandDao {
  public List<BlackHandRole> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int totalNumberOfPlayers);
}