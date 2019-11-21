package com.partyrgame.blackhandservice.dao;

import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.model.PlayerTurn;

public interface BlackHandDao {
  public List<BlackHandGame> getBlackHandRawDetails(String roomName);

  public BlackHand getBlackHandDetails(String roomName);

  public int setPreferredFaction(String username, String roomName, String preferredFaction);

  public int selectDisplayName(String username, String roomName, String displayName);

  public int submitPlayerTurn(PlayerTurn turn);

  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int totalNumberOfPlayers);
}