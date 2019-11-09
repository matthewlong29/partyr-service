package com.partygames.partygamesservice.controller;

import java.util.List;
import java.util.Map;

import com.partygames.partygamesservice.model.Room;
import com.partygames.partygamesservice.service.LobbyService;
import com.partygames.partygamesservice.service.MessageService;
import com.partygames.partygamesservice.util.WebsocketConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LobbyController {
  @Autowired
  LobbyService lobbyService;

  @Autowired
  MessageService messageService;

  /**
   * createRoom.
   * 
   * @param body {"roomName": "game 1", "gameName": "Black Hand", "username": "timmy7"}
   */
  @MessageMapping(WebsocketConstants.ROOM_CREATE_SEND)
  public void createRoom(Map<String, String> body) {
    log.info("body: {}", body.toString());

    String username = body.get("username");
    String roomName = body.get("roomName");
    String gameName = body.get("gameName");

    lobbyService.createNewGameRoom(username, roomName, gameName);

    messageService.sendRoomMessage(lobbyService.getRooms());
  }

  /**
   * joinRoom.
   * 
   * @param body {"roomName": "game 1", "username": "timmy7"}
   */
  @MessageMapping(WebsocketConstants.ROOM_JOIN_SEND)
  public void joinRoom(Map<String, String> body) {
    log.info("body: {}", body.toString());

    String username = body.get("username");
    String roomName = body.get("roomName");

    lobbyService.joinGameRoom(username, roomName);

    messageService.sendRoomMessage(lobbyService.getRooms());
  }

  /**
   * leaveRoom.
   * 
   * @param body {"roomName": "game 1", "username": "timmy7"}
   */
  @MessageMapping(WebsocketConstants.ROOM_LEAVE_SEND)
  public void leaveRoom(Map<String, String> body) {
    log.info("body: {}", body.toString());

    String username = body.get("username");
    String roomName = body.get("roomName");

    lobbyService.leaveGameRoom(username, roomName);

    messageService.sendRoomMessage(lobbyService.getRooms());
  }

  /**
   * deleteRoom.
   * 
   * @param body {"roomName": "game 1"}
   */
  @MessageMapping(WebsocketConstants.ROOM_DELETE_SEND)
  public void deleteRoom(Map<String, String> body) {
    log.info("body: {}", body.toString());

    String roomName = body.get("roomName");

    lobbyService.deleteGameRoom(roomName);

    messageService.sendRoomMessage(lobbyService.getRooms());
  }

  /**
   * toggleReadyStatus.
   * 
   * @param body {"roomName": "game 1", "username": "timmy7"}
   */
  @MessageMapping(WebsocketConstants.ROOM_TOGGLE_READY_STATUS)
  public void toggleReadyStatus(Map<String, String> body) {
    log.info("body: {}", body.toString());

    String username = body.get("username");
    String roomName = body.get("roomName");

    lobbyService.toggleReadyStatus(username, roomName);

    messageService.sendRoomMessage(lobbyService.getRooms());
  }

  /**
   * getLobby.
   */
  @GetMapping(value = "/api/game/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Room> getRooms() {
    return lobbyService.getRooms();
  }
}