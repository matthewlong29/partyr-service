package com.partyrgame.blackhandservice.service;

import com.partyrgame.blackhandservice.model.BlackHand;

public interface BlackHandTrialService {
  public BlackHand submitVote(String roomName);

  public BlackHand evaluateTrial(String roomName);
}