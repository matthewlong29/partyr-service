package com.partyrgame.blackhandservice.dao;

import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;

public interface BlackHandDao {
  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int totalNumberOfPlayers);
}