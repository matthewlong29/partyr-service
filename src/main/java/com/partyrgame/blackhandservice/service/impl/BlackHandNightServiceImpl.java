package com.partyrgame.blackhandservice.service.impl;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandPhase;
import com.partyrgame.blackhandservice.service.BlackHandNightService;
import com.partyrgame.blackhandservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackHandNightServiceImpl implements BlackHandNightService {
  @Autowired
  BlackHandService blackHandService;

  @Autowired
  BlackHandDao dao;

  /**
   * evaluateNight.
   */
  public BlackHand evaluateNight(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    dao.resetGameCycle(roomName);

    BlackHandNumberOfPlayers numOfPlayers = new BlackHandNumberOfPlayers();
    numOfPlayers.setBlackHandTotal(blackHand.getNumOfBlackHandRemaining());
    numOfPlayers.setMonstersTotal(blackHand.getNumOfMonsterRemaining());
    numOfPlayers.setTowniesTotal(blackHand.getNumOfTownieRemaining());

    dao.updateBlackHandGame(blackHand.getRoomName(), BlackHandPhase.DAY, numOfPlayers.getBlackHandTotal(),
        numOfPlayers.getMonstersTotal(), numOfPlayers.getTowniesTotal());

    return blackHandService.getBlackHandDetails(roomName);
  }
}