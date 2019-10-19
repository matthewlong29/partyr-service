package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.Game;

public interface GamesDao {
  public List<Game> getAllGames();

  public Game getGameByName(String gameName);
}