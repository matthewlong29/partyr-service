package com.partyrgame.blackhandservice.service;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.PlayerTurn;

public interface BlackHandDayService {
  public BlackHand submitPlayerTurn(PlayerTurn turn);
}