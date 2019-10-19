package com.partygames.partygamesservice.service;

import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandStartGame;

public interface BlackHandStartGameService {
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings);
}