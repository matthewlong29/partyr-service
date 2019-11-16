package com.partyrgame.gameservice.dao;

import java.util.List;

import com.partyrgame.gameservice.model.Game;

public interface GamesDao {
  public List<Game> getAllGames();

  public Game getGameByName(String gameName);
}