package com.partyrgame.roomservice.service.impl;

import java.util.List;

import com.partyrgame.roomservice.dao.LobbyDao;
import com.partyrgame.roomservice.model.Room;
import com.partyrgame.roomservice.service.LobbyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LobbyServiceImpl implements LobbyService {
  @Autowired
  LobbyDao lobbyDao;

  /**
   * createNewGameRoom.
   */
  public int createNewGameRoom(String username, String roomName, String gameName) {
    return lobbyDao.createNewGameRoom(username, roomName, gameName);
  }

  /**
   * joinGameRoom.
   */
  public int joinGameRoom(String username, String roomName) {
    return lobbyDao.joinGameRoom(username, roomName);
  }

  /**
   * leaveGameRoom.
   */
  public int leaveGameRoom(String username, String roomName) {
    return lobbyDao.leaveGameRoom(username, roomName);
  }

  /**
   * deleteGameRoom.
   */
  public int deleteGameRoom(String roomName) {
    return lobbyDao.deleteGameRoom(roomName);
  }

    /**
   * toggleReadyStatus.
   */
  public int toggleReadyStatus(String username, String roomName) {
    return lobbyDao.toggleReadyStatus(username, roomName);
  }

  /**
   * getRooms.
   */
  public List<Room> getRooms() {
    return lobbyDao.getRooms();
  }
}