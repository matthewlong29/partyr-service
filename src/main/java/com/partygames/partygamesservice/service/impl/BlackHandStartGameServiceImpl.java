package com.partygames.partygamesservice.service.impl;

import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandStartGame;
import com.partygames.partygamesservice.service.BlackHandStartGameService;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.stereotype.Service;

@Service
public class BlackHandStartGameServiceImpl implements BlackHandStartGameService {
  /**
   * startGame.
   */
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings) {
    PartyLogger.info(blackHandSettings.toString());
    BlackHandStartGame blackHandStartGame = new BlackHandStartGame();

    return blackHandStartGame;
  }
}