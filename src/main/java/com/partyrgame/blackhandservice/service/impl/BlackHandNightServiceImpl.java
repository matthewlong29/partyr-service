package com.partyrgame.blackhandservice.service.impl;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.service.BlackHandNightService;
import com.partyrgame.blackhandservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackHandNightServiceImpl implements BlackHandNightService {
  @Autowired
  BlackHandService blackHandService;

  /**
   * evaluateNight.
   */
  public BlackHand evaluateNight(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    return blackHand;
  }
}