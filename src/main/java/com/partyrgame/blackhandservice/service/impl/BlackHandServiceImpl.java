package com.partyrgame.blackhandservice.service.impl;

import java.util.List;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandPhase;
import com.partyrgame.blackhandservice.model.PlayerTurn;
import com.partyrgame.blackhandservice.service.BlackHandDayService;
import com.partyrgame.blackhandservice.service.BlackHandNightService;
import com.partyrgame.blackhandservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandServiceImpl implements BlackHandService {
  @Autowired
  BlackHandDao blackHandDao;

  @Autowired
  BlackHandDayService blackHandDayService;

  @Autowired
  BlackHandNightService blackHandNightService;

  /**
   * getBlackHandRawDetails.
   */
  public List<BlackHandGame> getBlackHandRawDetails(String roomName) {
    return blackHandDao.getBlackHandRawDetails(roomName);
  }

  /**
   * getBlackHandDetails.
   */
  public BlackHand getBlackHandDetails(String roomName) {
    log.debug("game details: {}", blackHandDao.getBlackHandDetails(roomName).toString());

    return blackHandDao.getBlackHandDetails(roomName);
  }

  /**
   * setPreferredFaction.
   */
  public BlackHand setPreferredFaction(String username, String roomName, String preferredFaction) {
    blackHandDao.setPreferredFaction(username, roomName, preferredFaction);

    return blackHandDao.getBlackHandDetails(roomName);
  }

  /**
   * selectDisplayName.
   */
  public BlackHand selectDisplayName(String username, String roomName, String displayName) {
    blackHandDao.selectDisplayName(username, roomName, displayName);

    return blackHandDao.getBlackHandDetails(roomName);
  }

  /**
   * completePhase.
   */
  public BlackHand completePhase(BlackHand blackHand, List<PlayerTurn> playerTurns) throws Exception {
    if (blackHand.getPhase().equals(BlackHandPhase.DAY)) {
      log.info("currently in day phase");
    } else if (blackHand.getPhase().equals(BlackHandPhase.NIGHT)) {
      log.info("currently in night phase");
    } else {
      throw new Exception("unable to complete game; phase not recognized"); // TODO: create custom phase exception
    }

    blackHand.setPhase(alternatePhase(blackHand.getPhase()));

    return new BlackHand();
  }

  /**
   * alternatePhase: switched from day phase to night phase and night phase to day
   * phase after all players have made their move.
   */
  private BlackHandPhase alternatePhase(BlackHandPhase phase) throws Exception {
    if (phase.equals(BlackHandPhase.DAY)) {
      log.info("alternating to night phase");
      return BlackHandPhase.NIGHT;
    } else if (phase.equals(BlackHandPhase.NIGHT)) {
      log.info("alternating to day phase");
      return BlackHandPhase.DAY;
    } else {
      throw new Exception("unable to complete game; phase not recognized"); // TODO: create custom phase exception
    }
  }
}