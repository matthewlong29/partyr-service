package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.Game;

public interface PartyGamesService {
  public List<Game> getGamesAvailable();
}