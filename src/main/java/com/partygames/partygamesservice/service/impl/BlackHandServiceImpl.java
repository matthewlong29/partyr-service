package com.partygames.partygamesservice.service.impl;

import java.util.List;
import java.util.Optional;

import com.partygames.partygamesservice.dao.BlackHandDao;
import com.partygames.partygamesservice.model.BlackHandFaction;
import com.partygames.partygamesservice.model.BlackHandRequiredNumberOfPlayers;
import com.partygames.partygamesservice.model.BlackHandRole;
import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandSettings.BlackHandPlayerPreferences;
import com.partygames.partygamesservice.model.BlackHandStartGame;
import com.partygames.partygamesservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandServiceImpl implements BlackHandService {
  @Autowired
  BlackHandDao blackHandDao;

  private int numberOfPlayers;

  private int requiredNumberOfMonsters;
  private int requiredNumberOfTownies;
  private int requiredNumberOfBlackHand;

  private int actualNumberOfMonsters;
  private int actualNumberOfTownies;
  private int actualNumberOfBlackHand;

  /**
   * startGame.
   */
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings) {
    // log.info(blackHandSettings.toString());
    List<BlackHandRole> availableRoles = getBlackHandRoles(); // take from this list as user preference
    BlackHandStartGame blackHandStartGame = new BlackHandStartGame();

    List<BlackHandPlayerPreferences> preferences = blackHandSettings.getPlayerPreferences();
    // log.info(preferences.toString());

    for (BlackHandPlayerPreferences preference : preferences) {
      log.info("user: {}", preference.getPlayer().getEmail());
      findFirstAvailableRole(availableRoles, preference.getPreferredFaction());
      // availableRoles.
    }

    return blackHandStartGame;
  }

  /**
   * getBlackHandRoles.
   */
  public List<BlackHandRole> getBlackHandRoles() {
    return blackHandDao.getBlackHandRoles();
  }

  /**
   * getBlackHandRequiredNumberOfPlayers.
   */
  public BlackHandRequiredNumberOfPlayers getBlackHandRequiredNumberOfPlayers(int playerTotal) {
    return blackHandDao.getBlackHandRequiredNumberOfPlayers(playerTotal);
  }

  /**
   * calculateGamePlayerNumbers: calculates the required number of players per
   * faction based off of the total number of players.
   */
  private void calculateGamePlayerNumbers() {

  }

  /**
   * findFirstAvailableFaction: scans list of available roles and removes that
   * role from the available roles list.
   */
  private BlackHandRole findFirstAvailableRole(List<BlackHandRole> availableRoles, BlackHandFaction desiredFaction) {
    Optional<BlackHandRole> selectedBlackHandRole = availableRoles.stream()
        .filter(s -> s.getFaction().equals(desiredFaction)).findFirst();

    if (selectedBlackHandRole.isPresent()) {
      log.info("Found a black hand role for this user matching the desired faction: {}", selectedBlackHandRole.get());
    } else {
      log.info("Could not find a black hand role for this user.");
    }

    return selectedBlackHandRole.get();
  }
}