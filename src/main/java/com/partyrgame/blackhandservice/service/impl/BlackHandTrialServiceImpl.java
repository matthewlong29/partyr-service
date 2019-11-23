package com.partyrgame.blackhandservice.service.impl;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.service.BlackHandService;
import com.partyrgame.blackhandservice.service.BlackHandTrialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackHandTrialServiceImpl implements BlackHandTrialService {
  @Autowired
  BlackHandService blackHandService;

  /**
   * submitVote.
   */
  public BlackHand submitVote(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    return blackHand;
  }

  /**
   * evaluateTrial.
   */
  public BlackHand evaluateTrial(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    return blackHand;
  }
}