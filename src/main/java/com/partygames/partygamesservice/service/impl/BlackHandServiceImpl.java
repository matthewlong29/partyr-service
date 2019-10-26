package com.partygames.partygamesservice.service.impl;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.dao.BlackHandDao;
import com.partygames.partygamesservice.model.blackhand.BlackHandFaction;
import com.partygames.partygamesservice.model.blackhand.BlackHandRole;
import com.partygames.partygamesservice.model.blackhand.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.blackhand.BlackHandSettings;
import com.partygames.partygamesservice.model.blackhand.BlackHandSettings.BlackHandPlayerPreferences;
import com.partygames.partygamesservice.model.blackhand.BlackHandStartGame;
import com.partygames.partygamesservice.model.blackhand.BlackHandStartGame.BlackHandPlayer;
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

    log.info("{};", availableRoles.toString());

    BlackHandStartGame blackHandStartGame = new BlackHandStartGame();
    List<BlackHandPlayerPreferences> preferences = blackHandSettings.getPlayerPreferences();
    int totalNumberOfPlayers = preferences.size();
    BlackHandNumberOfPlayers requiredNumber = getBlackHandNumberOfPlayers(totalNumberOfPlayers);

    log.info("required number of players per faction: {};", requiredNumber);

    for (BlackHandPlayerPreferences preference : preferences) {
      log.info("user: {}", preference.getPlayer().getEmail());
      assignPreferredRole(blackHandStartGame, availableRoles, preference, requiredNumber, actualNumber);
    }

    assignRemainingRole(blackHandStartGame, availableRoles, requiredNumber, actualNumber);

    log.info("actual number of players per faction: {};", actualNumber);

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
      log.info("found role for player: {};", availableRoles.get(playerPreference.getPreferredFaction()).get(0));
      blackHandPlayer.setRole(availableRoles.get(playerPreference.getPreferredFaction()).get(0));
      availableRoles.get(playerPreference.getPreferredFaction()).remove(0);
      incrementNumberOfPlayersPerFaction(playerPreference.getPreferredFaction(), actualNumber);
    } else {
      log.info("unable to set role for player;");
    }

    blackHandStartGame.getPlayerRoles().add(blackHandPlayer);
  }

  /**
   * assignRemainingRole: scan for instance where a players role has not been set
   * and fill that role with what is missing.
   * 
   * TODO: refactor..
   */
  private void assignRemainingRole(BlackHandStartGame blackHandStartGame,
      HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles, BlackHandNumberOfPlayers requiredNumber,
      BlackHandNumberOfPlayers actualNumber) {
    for (BlackHandPlayer blackHandPlayer : blackHandStartGame.getPlayerRoles()) {
      if (blackHandPlayer.getRole() == null) {
        log.info("need to assign a role for {};", blackHandPlayer.getPlayer().getEmail());
        if (requiredNumber.getBlackHandTotal() > actualNumber.getBlackHandTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.BlackHand).get(0));
          blackHandPlayer.setRole(availableRoles.get(BlackHandFaction.BlackHand).get(0));
          availableRoles.get(BlackHandFaction.BlackHand).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.BlackHand, actualNumber);
        } else if (requiredNumber.getMonstersTotal() > actualNumber.getMonstersTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.Monster).get(0));
          blackHandPlayer.setRole(availableRoles.get(BlackHandFaction.Monster).get(0));
          availableRoles.get(BlackHandFaction.Monster).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.Monster, actualNumber);
        } else if (requiredNumber.getTowniesTotal() > actualNumber.getTowniesTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.Townie).get(0));
          blackHandPlayer.setRole(availableRoles.get(BlackHandFaction.Townie).get(0));
          availableRoles.get(BlackHandFaction.Townie).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.Townie, actualNumber);
        }
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