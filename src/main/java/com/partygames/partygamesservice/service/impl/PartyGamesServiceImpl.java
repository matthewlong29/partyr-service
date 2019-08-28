package com.partygames.partygamesservice.service.impl;

import com.partygames.partygamesservice.service.PartyGamesService;

import org.springframework.stereotype.Service;

@Service
public class PartyGamesServiceImpl implements PartyGamesService {
  /**
   * welcome.
   */
  public String welcome() {
    return "Welcome to the partyyy";
  }
}