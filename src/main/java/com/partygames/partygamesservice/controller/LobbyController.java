package com.partygames.partygamesservice.controller;

import java.util.List;
import java.util.Map;

import com.partygames.partygamesservice.model.Room;
import com.partygames.partygamesservice.service.LobbyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/game")
public class LobbyController {
  @Autowired
  LobbyService lobbyService;

  /**
   * createLobby.
   */
  @PostMapping(value = "/create-room", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int createLobby(@RequestBody Map<String, String> body) {
    log.info("body: {}", body.toString());

    String email = body.get("email");
    String roomName = body.get("roomName");
    String gameName = body.get("gameName");

    return lobbyService.createNewGameRoom(email, roomName, gameName);
  }

  /**
   * joinLobby.
   */
  @PostMapping(value = "/join-room", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int joinLobby(@RequestBody Map<String, String> body) {
    log.info("body: {}", body.toString());
    
    String email = body.get("email");
    String roomName = body.get("roomName");

    return lobbyService.joinGameRoom(email, roomName);
  }

  /**
   * getLobby
   */
  @GetMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Room> getRooms() {
    return lobbyService.getRooms();
  }
}