package com.partygames.partygamesservice.controller;

import java.util.List;
import java.util.Map;

import com.partygames.partygamesservice.model.Room;
import com.partygames.partygamesservice.service.LobbyService;
import com.partygames.partygamesservice.service.MessageService;

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
   * @param body {"roomName": "best game of black hand ever4", "gameName": "Black
   *             Hand", "email": "timmy7@gmail.com"}
   */
  @MessageMapping("/create-room")
  public void createRoom(Map<String, String> body) {
    log.info("body: {}", body.toString());

    String email = body.get("email");
    String roomName = body.get("roomName");
    String gameName = body.get("gameName");

    lobbyService.createNewGameRoom(email, roomName, gameName);

    messageService.sendRoomMessage(lobbyService.getRooms());
  }

  /**
   * joinRoom.
   * 
   * @param body {"roomName": "best game of black hand ever4", "email":
   *             "timmy7@gmail.com"}
   */
  @MessageMapping("/join-room")
  public void joinRoom(Map<String, String> body) {
    log.info("body: {}", body.toString());

    String email = body.get("email");
    String roomName = body.get("roomName");

    lobbyService.joinGameRoom(email, roomName);

    messageService.sendRoomMessage(lobbyService.getRooms());
  }

  /**
   * getLobby
   */
  @GetMapping(value = "/api/game/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Room> getRooms() {
    return lobbyService.getRooms();
  }
}