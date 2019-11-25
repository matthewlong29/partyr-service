package com.partyrgame.blackhandservice.service.impl;

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
import com.partyrgame.roomservice.dao.LobbyDao;

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

  @Autowired
  LobbyDao lobbyDao;

  /**
   * startGame: returns data necessary to start a game of black.
   * 
   * TODO: add randomization for assigning roles;
   * 
   * TODO: update get_black_hand_game stored proc to also return start time for
   * game
   * 
   * TODO: consider adding more to lobby table (i.e. phase, numRemaining,
   * lastPlayerToDie, playerOnTrial, etc...?)
   */
  public BlackHand startGame(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    if (null != blackHand.getGameStartTime()) {
      log.info("this game [{}] has already started [{}]", blackHand.getRoomName(), blackHand.getGameStartTime());

      return blackHand;
    }

    lobbyDao.startGame(roomName);

    BlackHandNumberOfPlayers actualNumber = new BlackHandNumberOfPlayers();
    HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles = getBlackHandRoles();

    log.debug("available roles: {}", availableRoles.toString());
    log.debug("black hand game: {}", blackHand.toString());

    blackHand.setRoomName(roomName);
    blackHand.setPhase(BlackHandConstants.DAY); // start game in DAY phase
    blackHand.setPlayersTurnRemaining(getAllPlayersDisplayNames(blackHand.getAlivePlayers()));

    List<BlackHand.BlackHandPlayer> players = blackHand.getAlivePlayers();
    int totalNumberOfPlayers = players.size();

    BlackHandNumberOfPlayers requiredNumber = getBlackHandNumberOfPlayers(totalNumberOfPlayers);
    blackHand.setNumOfBlackHandRemaining(requiredNumber.getBlackHandTotal());
    blackHand.setNumOfMonsterRemaining(requiredNumber.getMonstersTotal());
    blackHand.setNumOfTownieRemaining(requiredNumber.getTowniesTotal());

    log.debug("required number of players per faction: {};", requiredNumber);

    for (BlackHand.BlackHandPlayer player : players) {
      log.debug("user: {}", player.getUsername());
      assignPreferredRole(blackHand, availableRoles, player, requiredNumber, actualNumber);
    }

    try {
      assignRemainingRole(blackHand, availableRoles, requiredNumber, actualNumber);
    } catch (Exception e) {
      log.error("exception occurred when trying to assign role to player: {}", e.getMessage());
    }

    log.debug("actual number of players per faction: {};", actualNumber);

    blackHandDao.updateBlackHandGame(roomName, BlackHandConstants.DAY);

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
      log.info("at least 5 players are needed to play the Black Hand!");
      playerTotal = 5;
    } else if (playerTotal > 15) {
      log.info("at most 15 players can play the Black Hand!");
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
      int turnPriority = availableRoles.get(player.getPreferredFaction()).get(0).getRolePriority();

      player.setRole(availableRoles.get(player.getPreferredFaction()).get(0));
      player.setActualFaction(player.getPreferredFaction());
      player.setTurnPriority(turnPriority);

      blackHandDao.updateBlackHandGameForPlayer(player.getUsername(), blackHand.getRoomName(),
          player.getRole().getName(), player.getActualFaction().toString(), turnPriority);
      availableRoles.get(player.getPreferredFaction()).remove(0);

      incrementNumberOfPlayersPerFaction(player.getPreferredFaction(), actualNumber);
    } else {
      log.info("unable to set role for player;");
    }
  }

  /**
   * assignRemainingRole: scan for instance where a players role has not been set
   * and fill that role with what is missing.
   */
  private void assignRemainingRole(BlackHand blackHand, HashMap<BlackHandFaction, List<BlackHandRole>> availableRoles,
      BlackHandNumberOfPlayers requiredNumber, BlackHandNumberOfPlayers actualNumber) throws Exception {
    for (BlackHandPlayer player : blackHand.getAlivePlayers()) {
      int turnPriority;

      if (player.getRole() == null) {
        log.info("need to assign a role for {};", player.getUsername());
        if (requiredNumber.getBlackHandTotal() > actualNumber.getBlackHandTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.BlackHand).get(0));
          player.setRole(availableRoles.get(BlackHandFaction.BlackHand).get(0));
          player.setActualFaction(BlackHandFaction.BlackHand);
          turnPriority = availableRoles.get(BlackHandFaction.BlackHand).get(0).getRolePriority();
          player.setTurnPriority(turnPriority);
          availableRoles.get(BlackHandFaction.BlackHand).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.BlackHand, actualNumber);
        } else if (requiredNumber.getMonstersTotal() > actualNumber.getMonstersTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.Monster).get(0));
          player.setRole(availableRoles.get(BlackHandFaction.Monster).get(0));
          player.setActualFaction(BlackHandFaction.Monster);
          turnPriority = availableRoles.get(BlackHandFaction.Monster).get(0).getRolePriority();
          player.setTurnPriority(turnPriority);
          availableRoles.get(BlackHandFaction.Monster).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.Monster, actualNumber);
        } else if (requiredNumber.getTowniesTotal() > actualNumber.getTowniesTotal()) {
          log.info("found role for player: {};", availableRoles.get(BlackHandFaction.Townie).get(0));
          player.setRole(availableRoles.get(BlackHandFaction.Townie).get(0));
          player.setActualFaction(BlackHandFaction.Townie);
          turnPriority = availableRoles.get(BlackHandFaction.Townie).get(0).getRolePriority();
          player.setTurnPriority(turnPriority);
          availableRoles.get(BlackHandFaction.Townie).remove(0);
          incrementNumberOfPlayersPerFaction(BlackHandFaction.Townie, actualNumber);
        } else {
          throw new Exception("cannot assign player a role");
        }

        blackHandDao.updateBlackHandGameForPlayer(player.getUsername(), blackHand.getRoomName(),
            player.getRole().getName(), player.getActualFaction().toString(), turnPriority);
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