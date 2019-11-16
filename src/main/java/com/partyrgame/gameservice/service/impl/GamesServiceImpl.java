package com.partyrgame.gameservice.service.impl;

import java.util.List;

import com.partyrgame.gameservice.dao.GamesDao;
import com.partyrgame.gameservice.model.Game;
import com.partyrgame.gameservice.service.GamesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamesServiceImpl implements GamesService {
  @Autowired
  GamesDao gamesDao;

  /**
   * getGamesAvailable: returns a list of games available.
   */
  public List<Game> getAllGames() {
    return gamesDao.getAllGames();
  }

  /**
   * getGameByName: returns information about a specific game.
   */
  public Game getGameByName(String gameName) {
    return gamesDao.getGameByName(gameName);
  }
}