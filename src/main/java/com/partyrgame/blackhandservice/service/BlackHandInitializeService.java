package com.partyrgame.blackhandservice.service;

import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;

public interface BlackHandInitializeService {
  public BlackHand startGame(String roomName);

  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int playerTotal);

  public List<String> getAllPlayerUsernames(List<BlackHand.BlackHandPlayer> players);

  public List<String> getAllPlayersDisplayNames(List<BlackHand.BlackHandPlayer> players);
}