package com.partygames.partygamesservice.service.impl;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.dao.BlackHandDao;
import com.partygames.partygamesservice.model.BlackHandFaction;
import com.partygames.partygamesservice.model.BlackHandFactionRoles.BlackHandRole;
import com.partygames.partygamesservice.model.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandSettings.BlackHandPlayerPreferences;
import com.partygames.partygamesservice.model.BlackHandStartGame;
import com.partygames.partygamesservice.model.BlackHandStartGame.BlackHandPlayer;
import com.partygames.partygamesservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandServiceImpl implements BlackHandService {
  @Autowired
  BlackHandDao blackHandDao;

  /**
   * startGame: returns data necessary to start a game of black
   */
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings) {
    BlackHandNumberOfPlayers actualNumber = new BlackHandNumberOfPlayers();
    HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles = getBlackHandRoles();

    log.info(availableRoles.toString());

    BlackHandStartGame blackHandStartGame = new BlackHandStartGame();
    List<BlackHandPlayerPreferences> preferences = blackHandSettings.getPlayerPreferences();
    int totalNumberOfPlayers = preferences.size();
    BlackHandNumberOfPlayers requiredNumber = getBlackHandNumberOfPlayers(totalNumberOfPlayers);

    log.info("required number of players per faction: {}", requiredNumber);

    for (BlackHandPlayerPreferences preference : preferences) {
      log.info("user: {}", preference.getPlayer().getEmail());
      assignPreferredRole(blackHandStartGame, availableRoles, preference, requiredNumber, actualNumber);
      assignRemainingRole(blackHandStartGame, availableRoles, preference, requiredNumber, actualNumber);
    }

    log.info("actual number of players per faction: {}", actualNumber);

    return blackHandStartGame;
  }

  /**
   * getBlackHandRoles: returns a list containing all black hand game roles.
   */
  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles() {
    return blackHandDao.getBlackHandRoles();
  }

  /**
   * getBlackHandNumberOfPlayers: returns an object containing the required number
   * of Monsters, BlackHands, and Townies.
   */
  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int playerTotal) {
    return blackHandDao.getBlackHandNumberOfPlayers(playerTotal);
  }

  /**
   * findFirstAvailableFaction: scans list of available roles, sets player that
   * role, and removes role from the available roles list. Player will be set, but
   * role may not yet be if their preference cannot be met.
   */
  private void assignPreferredRole(BlackHandStartGame blackHandStartGame,
      HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles, BlackHandPlayerPreferences playerPreference,
      BlackHandNumberOfPlayers requiredNumber, BlackHandNumberOfPlayers actualNumber) {
    BlackHandPlayer blackHandPlayer = new BlackHandPlayer();
    blackHandPlayer.setPlayer(playerPreference.getPlayer());

    if (availableRoles.containsKey(playerPreference.getPreferredFaction())
        && isLessThanRequired(playerPreference.getPreferredFaction(), requiredNumber, actualNumber)) {
      blackHandPlayer.setRole(availableRoles.get(playerPreference.getPreferredFaction()).get(0));
      availableRoles.get(playerPreference.getPreferredFaction()).remove(0);
      incrementNumberOfPlayersPerFaction(playerPreference.getPreferredFaction(), actualNumber);
    }

    blackHandStartGame.getPlayerRoles().add(blackHandPlayer);
  }

  /**
   * assignRemainingRole: scan for instance where a players role has not been set
   * and fill that role with what is missing.
   */
  private void assignRemainingRole(BlackHandStartGame blackHandStartGame,
      HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles, BlackHandPlayerPreferences playerPreference,
      BlackHandNumberOfPlayers requiredNumber, BlackHandNumberOfPlayers actualNumber) {
    for (BlackHandPlayer blackHandPlayer : blackHandStartGame.getPlayerRoles()) {
      if (blackHandPlayer.getRole() == null) {
        log.info("need to assign a role for {}", blackHandPlayer.getPlayer().getEmail());
      }
    }
  }

  /**
   * incrementNumberOfPlayersPerFaction.
   */
  private void incrementNumberOfPlayersPerFaction(BlackHandFaction faction, BlackHandNumberOfPlayers actualNumber) {
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
   * isLessThanRequired: returns true if the actual number of players per faction
   * is less than the required number of players per faction.
   */
  private boolean isLessThanRequired(BlackHandFaction faction, BlackHandNumberOfPlayers requiredNumber,
      BlackHandNumberOfPlayers actualNumber) {
    switch (faction) {
    case BlackHand:
      return requiredNumber.getBlackHandTotal() > actualNumber.getBlackHandTotal();
    case Monster:
      return requiredNumber.getMonstersTotal() > actualNumber.getMonstersTotal();
    case Townie:
      return requiredNumber.getTowniesTotal() > actualNumber.getTowniesTotal();
    default:
      return false; // invalid faction
    }
  }
}