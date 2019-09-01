package com.partygames.partygamesservice.controller;

import java.util.List;

import com.partygames.partygamesservice.dao.PartyGamesDao;
import com.partygames.partygamesservice.model.Game;
import com.partygames.partygamesservice.model.User;
import com.partygames.partygamesservice.service.PartyGamesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartyGamesController {
  @Autowired
  PartyGamesService partyGameService;

  @Autowired
  PartyGamesDao partyGamesDao;

  /**
   * welcome.
   */
  @GetMapping(value = "/welcome", produces = MediaType.TEXT_PLAIN_VALUE)
  public String welcome() {
    return "welcome! party games service up";
  }

  /**
   * getGames: returns list of games available.
   */
  @GetMapping(value = "/games-available", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Game> getGames() {
    return partyGameService.getGamesAvailable();
  }

  /**
   * getUsers.
   */
  @GetMapping(value = "/get-users", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getUsers() {
    return partyGamesDao.getAllUsers();
  }
}