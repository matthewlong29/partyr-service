package com.partygames.partygamesservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.partygames.partygamesservice.model.Game;
import com.partygames.partygamesservice.service.PartyGamesService;

import org.springframework.stereotype.Service;

@Service
public class PartyGamesServiceImpl implements PartyGamesService {
  /**
   * getGamesAvailable: returns a list of games available.
   */
  public List<Game> getGamesAvailable() {
    List<Game> games = new ArrayList<>();

    Game gameOne = new Game();
    gameOne.setNumberOfPlayers(2);
    gameOne.setTitle("Game One");
    games.add(gameOne);

    Game gameTwo = new Game();
    gameTwo.setNumberOfPlayers(4);
    gameTwo.setTitle("Game Two");
    games.add(gameTwo);

    return games;
  }
}