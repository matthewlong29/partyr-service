package com.partyrgame.blackhandservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;
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
   * getBlackHandRawDetails: method is for testing purposes. It's a BlackHand
   * object that is actually used to play the game. See getBlackHand.
   */
  @GetMapping(value = "/raw-details/{roomName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<BlackHandGame> getBlackHandRawDetails(@PathVariable String roomName) {
    return blackHandService.getBlackHandRawDetails(roomName);
  }

  /**
   * getBlackHandDetails.
   */
  @GetMapping(value = "/details/{roomName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BlackHand getBlackHandDetails(@PathVariable String roomName) {
    log.info("game details: {}", blackHandService.getBlackHandDetails(roomName).toString());
    return blackHandService.getBlackHandDetails(roomName);
  }

  /**
   * setPreferredFaction.
   * 
   * @param body {"roomName": "game 1", "username": "cheesecake",
   *             "preferredFaction": "Monster"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_SELECT_PREFERRED_FACTION)
  public void setPreferredFaction(Map<String, String> body) {
    log.info("body: {}", body.toString());

    String username = body.get("username");
    String roomName = body.get("roomName");
    String preferredFaction = body.get("preferredFaction");

    blackHandService.setPreferredFaction(username, roomName, preferredFaction);

    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    messageService.sendBlackHandMessage(blackHand);
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
   * 
   * @param body {"roomName": "game 1"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_START_SEND)
  public void startBlackHandGame(@RequestBody Map<String, String> body) {
    log.info("body: {}", body.toString());

    String roomName = body.get("roomName");

    BlackHand blackHand = blackHandInitializeService.startGame(roomName);

    messageService.sendBlackHandMessage(blackHand);
  }
}