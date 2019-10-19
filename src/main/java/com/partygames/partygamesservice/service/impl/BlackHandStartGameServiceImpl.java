package com.partygames.partygamesservice.service.impl;

import com.partygames.partygamesservice.model.BlackHandStartGame;
import com.partygames.partygamesservice.service.BlackHandStartGameService;

import org.springframework.stereotype.Service;

@Service
public class BlackHandStartGameServiceImpl implements BlackHandStartGameService {
  /**
   * startGame.
   */
  public BlackHandStartGame startGame() {
    return new BlackHandStartGame();
  }
}