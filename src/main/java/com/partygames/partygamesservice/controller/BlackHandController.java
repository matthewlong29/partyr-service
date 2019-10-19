package com.partygames.partygamesservice.controller;

import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandStartGame;
import com.partygames.partygamesservice.service.BlackHandStartGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class BlackHandController {
  @Autowired
  BlackHandStartGameService blackHandStartGameService;

  /**
   * startBlackHandGame: returns json needed to start a new game of the black
   * hand.
   */
  @GetMapping(value = "/start-black-hand", produces = MediaType.APPLICATION_JSON_VALUE)
  public BlackHandStartGame startBlackHandGame(@RequestBody BlackHandSettings blackHandSettings) {
    return blackHandStartGameService.startGame(blackHandSettings);
  }
}