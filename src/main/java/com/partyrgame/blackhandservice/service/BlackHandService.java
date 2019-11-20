package com.partyrgame.blackhandservice.service;

import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.PlayerTurn;

public interface BlackHandService {
  public List<BlackHandGame> getBlackHandRawDetails(String roomName);

  public BlackHand getBlackHandDetails(String roomName);

  public BlackHand setPreferredFaction(String username, String roomName, String preferredFaction);

  public BlackHand completePhase(BlackHand blackHand, List<PlayerTurn> playerTurns) throws Exception;

  public BlackHand selectDisplayName(String username, String roomName, String displayName);
}