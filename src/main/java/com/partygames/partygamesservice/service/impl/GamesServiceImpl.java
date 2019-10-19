package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.GamesDao;
import com.partygames.partygamesservice.model.Game;
import com.partygames.partygamesservice.service.GamesService;

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