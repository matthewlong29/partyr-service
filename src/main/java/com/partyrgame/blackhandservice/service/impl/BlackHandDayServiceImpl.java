package com.partyrgame.blackhandservice.service.impl;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
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

  @Autowired
  BlackHandDao blackHandDao;

  /**
   * evaluateDay.
   */
  public BlackHand evaluateDay(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    return blackHand;
  }

  /**
   * submitPlayerTurn.
   */
  public BlackHand submitPlayerTurn(PlayerTurn turn) {
    blackHandDao.submitPlayerTurn(turn);

    return blackHandService.getBlackHandDetails(turn.getRoomName());
  }
}