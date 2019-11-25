package com.partyrgame.blackhandservice.service;

import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;

public interface BlackHandService {
  public List<BlackHandGame> getBlackHandRawDetails(String roomName);

  public BlackHand getBlackHandDetails(String roomName);

  public BlackHand setPreferredFaction(String username, String roomName, String preferredFaction);

  public BlackHand selectDisplayName(String username, String roomName, String displayName);

  public BlackHandPlayer getBlackHandPlayer(BlackHand blackHand, String username);

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int playerTotal);

  public List<String> getAllPlayerUsernames(List<BlackHand.BlackHandPlayer> players);

  public List<String> getAllPlayersDisplayNames(List<BlackHand.BlackHandPlayer> players);

  public void incrementNumberOfPlayersPerFaction(BlackHandFaction faction, BlackHandNumberOfPlayers actualNumber);

  public void decrementNumberOfPlayersPerFaction(BlackHandFaction faction, BlackHandNumberOfPlayers actualNumber);

  public BlackHandPlayer getPlayerOnTrial(BlackHand blackHand);
}