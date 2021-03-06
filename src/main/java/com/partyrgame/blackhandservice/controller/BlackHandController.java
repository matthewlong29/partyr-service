package com.partyrgame.blackhandservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.model.PlayerTurn;
import com.partyrgame.blackhandservice.service.BlackHandDayService;
import com.partyrgame.blackhandservice.service.BlackHandInitializeService;
import com.partyrgame.blackhandservice.service.BlackHandNightService;
import com.partyrgame.blackhandservice.service.BlackHandService;
import com.partyrgame.blackhandservice.service.BlackHandTrialService;
import com.partyrgame.socketservice.service.MessageService;
import com.partyrgame.socketservice.util.WebsocketConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
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
  BlackHandDayService dayService;

  @Autowired
  BlackHandTrialService trialService;

  @Autowired
  BlackHandNightService nightService;

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
    log.debug("game details: {}", blackHandService.getBlackHandDetails(roomName).toString());
    return blackHandService.getBlackHandDetails(roomName);
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
    return blackHandService.getBlackHandNumberOfPlayers(playerTotal);
  }

  /**
   * setPreferredFaction.
   * 
   * @param body {"roomName": "game 1", "username": "cheesecake",
   *             "preferredFaction": "Monster"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_SELECT_PREFERRED_FACTION)
  public void setPreferredFaction(Map<String, String> body, @DestinationVariable String channel) {
    log.debug("body: {}", body.toString());

    String username = body.get("username");
    String roomName = body.get("roomName");
    String preferredFaction = body.get("preferredFaction");

    blackHandService.setPreferredFaction(username, roomName, preferredFaction);

    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    messageService.sendBlackHandMessage(blackHand, channel);
  }

  /**
   * selectDisplayName.
   * 
   * @param body {"roomName": "game 1", "username": "cheesecake",
   *             "preferredFaction": "Monster"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_SELECT_DISPLAY_NAME)
  public void selectDisplayName(Map<String, String> body, @DestinationVariable String channel) {
    log.debug("body: {}", body.toString());

    String username = body.get("username");
    String roomName = body.get("roomName");
    String displayName = body.get("displayName");

    blackHandService.selectDisplayName(username, roomName, displayName);

    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    messageService.sendBlackHandMessage(blackHand, channel);
  }

  /**
   * startBlackHandGame: returns json needed to start a new game of the black
   * hand.
   * 
   * @param body {"roomName": "game 1"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_START_SEND)
  public void startBlackHandGame(@RequestBody Map<String, String> body, @DestinationVariable String channel) {
    log.debug("body: {}", body.toString());

    String roomName = body.get("roomName");

    BlackHand blackHand = blackHandInitializeService.startGame(roomName);

    messageService.sendBlackHandMessage(blackHand, channel);
  }

  /**
   * evaluateBlackHandDayPhase: returns json needed to go into trial phase of a
   * game of the black hand.
   * 
   * @param body {"roomName": "game 1"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_EVALUATE_DAY)
  public void evaluateBlackHandDayPhase(@RequestBody Map<String, String> body, @DestinationVariable String channel) {
    log.debug("body: {}", body.toString());

    String roomName = body.get("roomName");

    BlackHand blackHand = dayService.evaluateDay(roomName);

    messageService.sendBlackHandMessage(blackHand, channel);
  }

  /**
   * blackHandSubmitVote: returns json needed to complete a trial.
   * 
   * @param body {"roomName": "game 1", "username": "cheesecake", "vote":
   *             "GUILTY"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_SUBMIT_VOTE)
  public void blackHandSubmitVote(@RequestBody Map<String, String> body, @DestinationVariable String channel) {
    log.debug("body: {}", body.toString());

    String roomName = body.get("roomName");
    String username = body.get("username");
    String vote = body.get("vote"); // GUILTY or NOT_GUILTY

    BlackHand blackHand = trialService.submitPlayerVote(roomName, username, vote);

    messageService.sendBlackHandMessage(blackHand, channel);
  }

  /**
   * blackHandEvaluateTrial: returns json needed to complete a trial.
   * 
   * @param body {"roomName": "game 1"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_EVALUATE_TRIAL)
  public void blackHandEvaluateTrial(@RequestBody Map<String, String> body, @DestinationVariable String channel) {
    log.debug("body: {}", body.toString());

    String roomName = body.get("roomName");

    BlackHand blackHand = trialService.evaluateTrial(roomName);

    messageService.sendBlackHandMessage(blackHand, channel);
  }

  /**
   * evaluateBlackHandNightPhase: returns json needed to go into day phase of a
   * game of the black hand.
   * 
   * @param body {"roomName": "game 1"}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_EVALUATE_NIGHT)
  public void evaluateBlackHandNightPhase(@RequestBody Map<String, String> body, @DestinationVariable String channel) {
    log.debug("body: {}", body.toString());

    String roomName = body.get("roomName");

    BlackHand blackHand = nightService.evaluateNight(roomName);

    messageService.sendBlackHandMessage(blackHand, channel);
  }

  /**
   * blackHandSubmitTurn: returns json needed to start a new game of the black
   * hand.
   * 
   * @param body {"roomName": "game 1", "username": "cheesecake", "attacking":
   *             "cody", "blocking": "", "note": "on the first night i attacked
   *             cody."}
   */
  @MessageMapping(WebsocketConstants.BLACK_HAND_SUBMIT_TURN)
  public void blackHandSubmitTurn(@RequestBody PlayerTurn turn, @DestinationVariable String channel) {
    log.debug("player turn: {}", turn);

    BlackHand blackHand = dayService.submitPlayerTurn(turn);

    messageService.sendBlackHandMessage(blackHand, channel);
  }
}
