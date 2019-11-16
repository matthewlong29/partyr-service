package com.partyrgame.gameservice.service;

import java.util.List;

import com.partyrgame.gameservice.model.Game;

public interface GamesService {
  public List<Game> getAllGames();

  public Game getGameByName(String gameName);
}
