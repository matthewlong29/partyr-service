package com.partygames.partygamesservice.service.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.partygames.partygamesservice.dao.BlackHandDao;
import com.partygames.partygamesservice.model.blackhand.BlackHand;
import com.partygames.partygamesservice.model.blackhand.BlackHand.BlackHandPlayer;
import com.partygames.partygamesservice.model.blackhand.BlackHandFaction;
import com.partygames.partygamesservice.model.blackhand.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.blackhand.BlackHandRole;
import com.partygames.partygamesservice.model.blackhand.BlackHandSettings;
import com.partygames.partygamesservice.model.blackhand.BlackHandSettings.BlackHandPlayerPreferences;
import com.partygames.partygamesservice.service.BlackHandInitializeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandInitializeServiceImpl implements BlackHandInitializeService {
  @Autowired
  BlackHandDao blackHandDao;

  /**
   * startGame: returns data necessary to start a game of black
   */
  public BlackHand startGame(BlackHandSettings blackHandSettings) {
    BlackHandNumberOfPlayers actualNumber = new BlackHandNumberOfPlayers();
    HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles = getBlackHandRoles();

    log.info("{};", availableRoles.toString());

    BlackHand blackHand = new BlackHand();
    blackHand.setGameRoomName(blackHandSettings.getGameRoomName());
    blackHand.setPhase("DAY");
    blackHand.setPlayersTurnRemaining(getPlayers(blackHandSettings));

    List<BlackHandPlayerPreferences> preferences = blackHandSettings.getPlayerPreferences();
    int totalNumberOfPlayers = preferences.size();
    BlackHandNumberOfPlayers requiredNumber = getBlackHandNumberOfPlayers(totalNumberOfPlayers);

    log.info("required number of players per faction: {};", requiredNumber);

    for (BlackHandPlayerPreferences preference : preferences) {
      log.info("user: {}", preference.getUsername());
      assignPreferredRole(blackHand, availableRoles, preference, requiredNumber, actualNumber);
    }

    assignRemainingRole(blackHand, availableRoles, requiredNumber, actualNumber);

    log.info("actual number of players per faction: {};", actualNumber);

    sortPlayersByRolePriority(blackHand.getPlayers());

    return blackHand;
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
   * getPlayers.
   */
  public List<String> getPlayers(BlackHandSettings blackHandSettings) {
    return blackHandSettings.getPlayerPreferences().stream().map(player -> player.getUsername())
        .collect(Collectors.toList());
  }

  /**
   * findFirstAvailableFaction: scans list of available roles, sets player that
   * role, and removes role from the available roles list. Player will be set, but
   * role may not yet be if their preference cannot be met.
   */
  private void assignPreferredRole(BlackHand blackHand, HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles,
      BlackHandPlayerPreferences playerPreference, BlackHandNumberOfPlayers requiredNumber,
      BlackHandNumberOfPlayers actualNumber) {
    BlackHandPlayer blackHandPlayer = new BlackHandPlayer();
    blackHandPlayer.setUsername(playerPreference.getUsername());
    blackHandPlayer.setAlive(true); // initialize player to be alive
    blackHandPlayer.setDisplayName(playerPreference.getDisplayName());

    if (availableRoles.containsKey(playerPreference.getPreferredFaction())
        && isLessThanRequired(playerPreference.getPreferredFaction(), requiredNumber, actualNumber)) {
      log.info("found role for player: {};", availableRoles.get(playerPreference.getPreferredFaction()).get(0));
      blackHandPlayer.setRole(availableRoles.get(playerPreference.getPreferredFaction()).get(0));
      blackHandPlayer
          .setTurnPriority(availableRoles.get(playerPreference.getPreferredFaction()).get(0).getRolePriority());
      availableRoles.get(playerPreference.getPreferredFaction()).remove(0);
      incrementNumberOfPlayersPerFaction(playerPreference.getPreferredFaction(), actualNumber);
    } else {
      log.info("unable to set role for player;");
    }

    blackHand.getPlayers().add(blackHandPlayer);
  }

  /**
   * assignRemainingRole: scan for instance where a players role has not been set
   * and fill that role with what is missing.
   * 
   * TODO: refactor..
   */
  private void assignRemainingRole(BlackHand blackHand, HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles,
      BlackHandNumberOfPlayers requiredNumber, BlackHandNumberOfPlayers actualNumber) {
    for (BlackHandPlayer blackHandPlayer : blackHand.getPlayers()) {
      if (blackHandPlayer.getRole() == null) {
        log.info("need to assign a role for {};", blackHandPlayer.getUsername());
        if (requiredNumber.getBlackHandTotal() > actualNumber.getBlackHandTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.BlackHand).get(0));
          blackHandPlayer.setRole(availableRoles.get(BlackHandFaction.BlackHand).get(0));
          blackHandPlayer.setTurnPriority(availableRoles.get(BlackHandFaction.BlackHand).get(0).getRolePriority());
          availableRoles.get(BlackHandFaction.BlackHand).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.BlackHand, actualNumber);
        } else if (requiredNumber.getMonstersTotal() > actualNumber.getMonstersTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.Monster).get(0));
          blackHandPlayer.setRole(availableRoles.get(BlackHandFaction.Monster).get(0));
          blackHandPlayer.setTurnPriority(availableRoles.get(BlackHandFaction.Monster).get(0).getRolePriority());
          availableRoles.get(BlackHandFaction.Monster).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.Monster, actualNumber);
        } else if (requiredNumber.getTowniesTotal() > actualNumber.getTowniesTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.Townie).get(0));
          blackHandPlayer.setRole(availableRoles.get(BlackHandFaction.Townie).get(0));
          blackHandPlayer.setTurnPriority(availableRoles.get(BlackHandFaction.Townie).get(0).getRolePriority());
          availableRoles.get(BlackHandFaction.Townie).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.Townie, actualNumber);
        }
      }
    }
  }

  /**
   * sortPlayersByRolePriority.
   */
  private void sortPlayersByRolePriority(List<BlackHand.BlackHandPlayer> blackHandPlayers) {
    blackHandPlayers.sort(Comparator.comparing(BlackHand.BlackHandPlayer::getTurnPriority));
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