package com.partygames.partygamesservice.dao;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.model.Lobby;
import com.partygames.partygamesservice.model.blackhand.BlackHandFaction;
import com.partygames.partygamesservice.model.blackhand.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.blackhand.BlackHandRole;

public interface BlackHandDao {
  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int totalNumberOfPlayers);

  public int createNewGameLobby(Lobby lobby);

  public int joinGameLobby(Lobby lobby);
}