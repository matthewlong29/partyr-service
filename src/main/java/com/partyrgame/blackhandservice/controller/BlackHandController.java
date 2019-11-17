package com.partyrgame.blackhandservice.controller;

import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.model.BlackHandSettings;
import com.partyrgame.blackhandservice.service.BlackHandInitializeService;
import com.partyrgame.blackhandservice.service.BlackHandService;
import com.partyrgame.socketservice.service.MessageService;
import com.partyrgame.socketservice.util.WebsocketConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/game/black-hand")
public class BlackHandController {
  @Autowired
  BlackHandInitializeService blackHandInitializeService;

  @Autowired
  BlackHandService blackHandService;

  @Autowired
  MessageService messageService;

  /**
   * getBlackHandGameByRoom.
   */
  @GetMapping(value = "/details/{roomName}")
  public List<BlackHandGame> getBlackHandGameByRoom(@PathVariable String roomName) {
    return blackHandService.getBlackHandGameByRoom(roomName);
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

  /**
   * startBlackHandGame: returns json needed to start a new game of the black
   * hand.
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_START_SEND)
  public void startBlackHandGame(@RequestBody BlackHandSettings blackHandSettings) {
    BlackHand newBlackHandGame = blackHandInitializeService.startGame(blackHandSettings);
    log.info(newBlackHandGame.toString());
    messageService.sendBlackHandMessage(newBlackHandGame);
  }
}