package com.partyrgame.blackhandservice.service.impl;

import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.PlayerTurn;
import com.partyrgame.blackhandservice.service.BlackHandDayService;
import com.partyrgame.blackhandservice.service.BlackHandNightService;
import com.partyrgame.blackhandservice.service.BlackHandService;
import com.partyrgame.util.BlackHandConstants;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BlackHandServiceImpl implements BlackHandService {
  @Autowired
  BlackHandDayService blackHandDayService;

  @Autowired
  BlackHandNightService blackHandNightService;

  /**
   * completePhase.
   */
  public BlackHand completePhase(BlackHand blackHand, List<PlayerTurn> playerTurns) throws Exception {
    if (blackHand.getPhase().equals(BlackHandConstants.DAY)) {
      log.info("currently in day phase");
    } else if (blackHand.getPhase().equals(BlackHandConstants.NIGHT)) {
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
  private String alternatePhase(String phase) throws Exception {
    if (phase.equals(BlackHandConstants.DAY)) {
      log.info("alternating to night phase");
      return BlackHandConstants.NIGHT;
    } else if (phase.equals(BlackHandConstants.NIGHT)) {
      log.info("alternating to day phase");
      return BlackHandConstants.DAY;
    } else {
      throw new Exception("unable to complete game; phase not recognized"); // TODO: create custom phase exception
    }
  }
}