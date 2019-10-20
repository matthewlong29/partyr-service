package com.partygames.partygamesservice.dao;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.model.BlackHandFaction;
import com.partygames.partygamesservice.model.BlackHandFactionRoles.BlackHandRole;
import com.partygames.partygamesservice.model.BlackHandNumberOfPlayers;

public interface BlackHandDao {
  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int totalNumberOfPlayers);
}