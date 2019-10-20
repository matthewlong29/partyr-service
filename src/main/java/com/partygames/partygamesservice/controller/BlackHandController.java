package com.partygames.partygamesservice.controller;

import java.util.List;

import com.partygames.partygamesservice.model.BlackHandRequiredNumberOfPlayers;
import com.partygames.partygamesservice.model.BlackHandRole;
import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandStartGame;
import com.partygames.partygamesservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/game/black-hand")
public class BlackHandController {
  @Autowired
  BlackHandService blackHandService;

  /**
   * startBlackHandGame: returns json needed to start a new game of the black
   * hand.
   */
  @GetMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
  public BlackHandStartGame startBlackHandGame(@RequestBody BlackHandSettings blackHandSettings) {
    return blackHandService.startGame(blackHandSettings);
  }

  /**
   * getBlackHandRoles.
   */
  @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<BlackHandRole> getBlackHandRoles() {
    return blackHandService.getBlackHandRoles();
  }

  
  /**
   * getBlackHandRequiredNumberOfPlayers.
   */
  @GetMapping(value = "/player-total/{playerTotal}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BlackHandRequiredNumberOfPlayers getBlackHandRequiredNumberOfPlayers(@PathVariable int playerTotal) {
    return blackHandService.getBlackHandRequiredNumberOfPlayers(playerTotal);
  }
}