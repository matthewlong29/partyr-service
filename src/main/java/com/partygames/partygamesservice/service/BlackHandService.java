package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.BlackHandRequiredNumberOfPlayers;
import com.partygames.partygamesservice.model.BlackHandRole;
import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandStartGame;

public interface BlackHandService {
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings);

  public List<BlackHandRole> getBlackHandRoles();

  public BlackHandRequiredNumberOfPlayers getBlackHandRequiredNumberOfPlayers(int playerTotal);
}