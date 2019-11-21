package com.partyrgame.blackhandservice.service.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.service.BlackHandInitializeService;
import com.partyrgame.blackhandservice.service.BlackHandService;
import com.partyrgame.blackhandservice.util.BlackHandConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandInitializeServiceImpl implements BlackHandInitializeService {
  @Autowired
  BlackHandService blackHandService;

  @Autowired
  BlackHandDao blackHandDao;

  /**
   * startGame: returns data necessary to start a game of black.
   * 
   * TODO: if displayName is null then set it equal to username;
   * 
   * TODO: add randomization for assigning roles;
   * 
   * TODO: update lobby schema to include start time for a game;
   * 
   * TODO: update get_black_hand_game stored proc to also return start time for
   * game
   * 
   * TODO: consider adding more to lobby table (i.e. phase, numRemaining,
   * lastPlayerToDie, playerOnTrial, etc...?)
   */
  public BlackHand startGame(String roomName) {
    BlackHandNumberOfPlayers actualNumber = new BlackHandNumberOfPlayers();
    HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles = getBlackHandRoles();

    log.info("available roles: {}", availableRoles.toString());

    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    log.info("black hand game: {}", blackHand.toString());

    blackHand.setRoomName(roomName);
    blackHand.setPhase(BlackHandConstants.DAY); // start game in DAY phase
    blackHand.setPlayersTurnRemaining(getAllPlayersDisplayNames(blackHand.getPlayers()));

    List<BlackHand.BlackHandPlayer> players = blackHand.getPlayers();
    int totalNumberOfPlayers = players.size();

    BlackHandNumberOfPlayers requiredNumber = getBlackHandNumberOfPlayers(totalNumberOfPlayers);
    blackHand.setNumOfBlackHandRemaining(requiredNumber.getBlackHandTotal());
    blackHand.setNumOfMonsterRemaining(requiredNumber.getMonstersTotal());
    blackHand.setNumOfTownieRemaining(requiredNumber.getTowniesTotal());

    log.info("required number of players per faction: {};", requiredNumber);

    for (BlackHand.BlackHandPlayer player : players) {
      log.info("user: {}", player.getUsername());
      assignPreferredRole(blackHand, availableRoles, player, requiredNumber, actualNumber);
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
    if (playerTotal < 5) {
      log.info("At least 5 players are needed to play the Black Hand!");
      playerTotal = 5;
    } else if (playerTotal > 15) {
      log.info("At most 15 players can play the Black Hand!");
      playerTotal = 15;
    }

    return blackHandDao.getBlackHandNumberOfPlayers(playerTotal);
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
   * findFirstAvailableFaction: scans list of available roles, sets player that
   * role, and removes role from the available roles list. Player will be set, but
   * role may not yet be if their preference cannot be met.
   * 
   * TODO: update db when assigning player
   */
  private void assignPreferredRole(BlackHand blackHand, HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles,
      BlackHand.BlackHandPlayer player, BlackHandNumberOfPlayers requiredNumber,
      BlackHandNumberOfPlayers actualNumber) {
    if (availableRoles.containsKey(player.getPreferredFaction())
        && isLessThanRequired(player.getPreferredFaction(), requiredNumber, actualNumber)) {
      log.info("found role for player: {};", availableRoles.get(player.getPreferredFaction()).get(0));
      player.setRole(availableRoles.get(player.getPreferredFaction()).get(0));
      player.setTurnPriority(availableRoles.get(player.getPreferredFaction()).get(0).getRolePriority());
      blackHandDao.setRoleForPlayer(player.getUsername(), blackHand.getRoomName(), player.getRole().getName());
      availableRoles.get(player.getPreferredFaction()).remove(0);
      incrementNumberOfPlayersPerFaction(player.getPreferredFaction(), actualNumber);
    } else {
      log.info("unable to set role for player;");
    }
  }

  /**
   * assignRemainingRole: scan for instance where a players role has not been set
   * and fill that role with what is missing.
   * 
   * TODO: refactor..
   * 
   * TODO: update db when assigning player
   */
  private void assignRemainingRole(BlackHand blackHand, HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles,
      BlackHandNumberOfPlayers requiredNumber, BlackHandNumberOfPlayers actualNumber) {
    for (BlackHandPlayer player : blackHand.getPlayers()) {
      if (player.getRole() == null) {
        log.info("need to assign a role for {};", player.getUsername());
        if (requiredNumber.getBlackHandTotal() > actualNumber.getBlackHandTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.BlackHand).get(0));
          player.setRole(availableRoles.get(BlackHandFaction.BlackHand).get(0));
          player.setTurnPriority(availableRoles.get(BlackHandFaction.BlackHand).get(0).getRolePriority());
          availableRoles.get(BlackHandFaction.BlackHand).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.BlackHand, actualNumber);
        } else if (requiredNumber.getMonstersTotal() > actualNumber.getMonstersTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.Monster).get(0));
          player.setRole(availableRoles.get(BlackHandFaction.Monster).get(0));
          player.setTurnPriority(availableRoles.get(BlackHandFaction.Monster).get(0).getRolePriority());
          availableRoles.get(BlackHandFaction.Monster).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.Monster, actualNumber);
        } else if (requiredNumber.getTowniesTotal() > actualNumber.getTowniesTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.Townie).get(0));
          player.setRole(availableRoles.get(BlackHandFaction.Townie).get(0));
          player.setTurnPriority(availableRoles.get(BlackHandFaction.Townie).get(0).getRolePriority());
          availableRoles.get(BlackHandFaction.Townie).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.Townie, actualNumber);
        }

        blackHandDao.setRoleForPlayer(player.getUsername(), blackHand.getRoomName(), player.getRole().getName());
      }
    }
  }

  /**
   * sortPlayersByRolePriority.
   */
  private void sortPlayersByRolePriority(List<BlackHand.BlackHandPlayer> players) {
    players.sort(Comparator.comparing(BlackHand.BlackHandPlayer::getTurnPriority));
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