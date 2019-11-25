package com.partyrgame.blackhandservice.service;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;

public interface BlackHandTrialService {
  public BlackHand evaluateTrial(String roomName);

  public BlackHand submitPlayerVote(String roomName, String username, String vote);

  public BlackHandPlayer getPlayerOnTrial(BlackHand blackHand);
}