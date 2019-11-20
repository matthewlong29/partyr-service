package com.partyrgame.blackhandservice.service.impl;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.PlayerTurn;
import com.partyrgame.blackhandservice.service.BlackHandDayService;
import com.partyrgame.blackhandservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackHandDayServiceImpl implements BlackHandDayService {
  @Autowired
  BlackHandService blackHandService;

  /**
   * submitPlayerTurn.
   */
  public BlackHand submitPlayerTurn(PlayerTurn turn) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(turn.getRoomName());

    return blackHand;
  }
}