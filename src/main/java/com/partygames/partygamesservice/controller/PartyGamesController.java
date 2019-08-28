package com.partygames.partygamesservice.controller;

import com.partygames.partygamesservice.service.PartyGamesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartyGamesController {
  @Autowired
  PartyGamesService partyGameService;

  @GetMapping("/welcome")
  public String welcome() {
    return partyGameService.welcome();
  }
}