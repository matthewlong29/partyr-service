package com.partygames.partygamesservice.controller;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.model.blackhand.BlackHand;
import com.partygames.partygamesservice.model.blackhand.BlackHandFaction;
import com.partygames.partygamesservice.model.blackhand.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.blackhand.BlackHandRole;
import com.partygames.partygamesservice.model.blackhand.BlackHandSettings;
import com.partygames.partygamesservice.service.BlackHandInitializeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/game/black-hand")
public class BlackHandController {
  @Autowired
  BlackHandInitializeService blackHandInitializeService;

  /**
   * startBlackHandGame: returns json needed to start a new game of the black
   * hand.
   */
  @PostMapping(value = "/start", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public BlackHand startBlackHandGame(@RequestBody BlackHandSettings blackHandSettings) {
    return blackHandInitializeService.startGame(blackHandSettings);
  }

  /**
   * getBlackHandRoles.
   */
  @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles() {
    return blackHandInitializeService.getBlackHandRoles();
  }

  /**
   * getBlackHandNumberOfPlayers.
   */
  @GetMapping(value = "/player-total/{playerTotal}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(@PathVariable int playerTotal) {
    return blackHandInitializeService.getBlackHandNumberOfPlayers(playerTotal);
  }
}