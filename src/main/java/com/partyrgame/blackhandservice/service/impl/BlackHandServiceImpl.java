package com.partyrgame.blackhandservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.service.BlackHandService;
import com.partyrgame.blackhandservice.service.BlackHandTrialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandServiceImpl implements BlackHandService {
  @Autowired
  BlackHandDao dao;

  @Autowired
  BlackHandTrialService trialService;

  /**
   * getBlackHandRawDetails.
   */
  public List<BlackHandGame> getBlackHandRawDetails(String roomName) {
    return dao.getBlackHandRawDetails(roomName);
  }

  /**
   * getBlackHandDetails.
   */
  public BlackHand getBlackHandDetails(String roomName) {
    log.debug("game details: {}", dao.getBlackHandDetails(roomName).toString());

    return dao.getBlackHandDetails(roomName);
  }

  /**
   * setPreferredFaction.
   */
  public BlackHand setPreferredFaction(String username, String roomName, String preferredFaction) {
    dao.setPreferredFaction(username, roomName, preferredFaction);

    return dao.getBlackHandDetails(roomName);
  }

  /**
   * selectDisplayName.
   */
  public BlackHand selectDisplayName(String username, String roomName, String displayName) {
    dao.selectDisplayName(username, roomName, displayName);

    return dao.getBlackHandDetails(roomName);
  }

  /**
   * incrementNumberOfPlayersPerFaction.
   */
  public void incrementNumberOfPlayersPerFaction(BlackHandFaction faction, BlackHandNumberOfPlayers actualNumber) {
    switch (faction) {
    case BlackHand:
      actualNumber.incrementBlackHandTotal();
      break;
    case Monster:
      actualNumber.incrementMonstersTotal();
      break;
    case Townie:
      actualNumber.incrementTowniesTotal();
      break;
    default:
      break; // invalid faction
    }
  }

  /**
   * decrementNumberOfPlayersPerFaction.
   */
  public void decrementNumberOfPlayersPerFaction(BlackHandFaction faction, BlackHandNumberOfPlayers actualNumber) {
    switch (faction) {
    case BlackHand:
      actualNumber.decrementBlackHandTotal();
      break;
    case Monster:
      actualNumber.decrementMonstersTotal();
      break;
    case Townie:
      actualNumber.decrementTowniesTotal();
      break;
    default:
      break; // invalid faction
    }
  }

  /**
   * getBlackHandNumberOfPlayers: returns an object containing the required number
   * of Monsters, BlackHands, and Townies.
   */
  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int playerTotal) {
    if (playerTotal < 5) {
      log.info("at least 5 players are needed to play the Black Hand!");
      playerTotal = 5;
    } else if (playerTotal > 15) {
      log.info("at most 15 players can play the Black Hand!");
      playerTotal = 15;
    }

    return dao.getBlackHandNumberOfPlayers(playerTotal);
  }

  /**
   * getAllPlayerUsernames.
   */
  public List<String> getAllPlayerUsernames(List<BlackHand.BlackHandPlayer> players) {
    return players.stream().map(player -> player.getUsername()).collect(Collectors.toList());
  }

  /**
   * getAllPlayersDisplayNames: if a players display name was not set then their
   * username will be returned.
   */
  public List<String> getAllPlayersDisplayNames(List<BlackHand.BlackHandPlayer> players) {
    return players.stream()
        .map(player -> player.getDisplayName() == null ? player.getUsername() : player.getDisplayName())
        .collect(Collectors.toList());
  }

  /**
   * getBlackHandPlayer.
   */
  public BlackHandPlayer getBlackHandPlayer(BlackHand blackHand, String username) {
    return blackHand.getAlivePlayers().stream().filter(player -> player.getUsername().equals(username)).findFirst()
        .get();
  }

  /**
   * getPlayerOnTrial.
   */
  public BlackHandPlayer getPlayerOnTrial(BlackHand blackHand) {
    return trialService.getPlayerOnTrial(blackHand);
  }
}