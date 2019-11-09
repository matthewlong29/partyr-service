package com.partyrgame.controller;

import java.util.List;

import com.partyrgame.model.Game;
import com.partyrgame.service.GamesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class GamesController {
  @Autowired
  GamesService gamesService;

  /**
   * getGames: returns list of games available.
   */
  @GetMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Game> getGames() {
    return gamesService.getAllGames();
  }

  /**
   * getGame: returns list of games available.
   */
  @GetMapping(value = "/game/{gameName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Game getGameByName(@PathVariable String gameName) {
    return gamesService.getGameByName(gameName);
  }
}