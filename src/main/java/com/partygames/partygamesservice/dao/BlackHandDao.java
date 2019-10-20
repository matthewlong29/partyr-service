package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.BlackHandRequiredNumberOfPlayers;
import com.partygames.partygamesservice.model.BlackHandRole;

public interface BlackHandDao {
  public List<BlackHandRole> getBlackHandRoles();

  public BlackHandRequiredNumberOfPlayers getBlackHandRequiredNumberOfPlayers(int totalNumberOfPlayers);
}