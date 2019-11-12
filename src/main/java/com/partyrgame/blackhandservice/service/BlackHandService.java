package com.partyrgame.blackhandservice.service;

import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.PlayerTurn;

public interface BlackHandService {
  public BlackHand completePhase(BlackHand blackHand, List<PlayerTurn> playerTurns) throws Exception;
}