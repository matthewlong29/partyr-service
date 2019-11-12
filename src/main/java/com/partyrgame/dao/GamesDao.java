package com.partyrgame.dao;

import java.util.List;

import com.partyrgame.model.Game;

public interface GamesDao {
  public List<Game> getAllGames();

  public Game getGameByName(String gameName);
}